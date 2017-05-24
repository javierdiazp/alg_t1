package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IntersectionFinder {
  private final int B = 4096/Segment.SIZE;
  private final int M = 524288/Segment.SIZE; //0.5 MB
  private final int m = M/B; 
  
  private int N;
  private float range;
  private String root;
  private String output;
  
  /* measured values */
  private long time;
  private int sortOperations;
  private int noSortOperations;
  private int intersectionsNumber;
  
  public IntersectionFinder(String root, String output, int N, float range) {
    this.root = root;
    this.output = output;
    this.N = N;
    this.range = range;
  }
  
  public void distributionSweep() {
    long iniTime = System.currentTimeMillis();
    long finTime;
    noSortOperations = 0;
    intersectionsNumber = 0;
    
    //Sorter sorterX = new Sorter(root, N, 'x');
    //sorterX.mergeSort();
    Sorter sorterY = new Sorter(root, N, 'y');
    sorterY.mergeSort();
    sortOperations = sorterY.getOperations();
    FileWriter fr = null;
    BufferedWriter bw = null;
    try {
      fr = new FileWriter(output + ".txt");
      bw = new BufferedWriter(fr);
      bw.write("N = " + N); bw.newLine();
      bw.newLine();
      bw.write("Intersection points:"); bw.newLine();
      scanSlab(0f, range, bw);
      finTime = System.currentTimeMillis();
      time = finTime - iniTime; bw.newLine();
      bw.newLine();
      bw.write("Statistics:"); bw.newLine();
      bw.write("Time = " + time + " ms"); bw.newLine();
      bw.write("Sort Operations = " + sortOperations); bw.newLine();
      bw.write("No Sort Operations = " + noSortOperations); bw.newLine();
      bw.write("Number of Intersections = " + intersectionsNumber);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bw != null) {
          bw.close();
        }
        if (fr != null) {
          fr.close();
        }
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    }
  }
  
  private void scanSlab(float start, float end, BufferedWriter bw) throws IOException {
    IOHandler handler = new IOHandler();
    int page = 0;
    int i, j, c; 
    float[] point;
    List<Segment> bufferY = handler.read(root + "_sortedByY", page);
    noSortOperations++;
    List<List<Segment>> listA = new ArrayList<List<Segment>>();
    for (int k = 0; k < m; k++) {
      listA.add(new ArrayList<Segment>());
    }
    Segment currsegment, auxsegment;
    
    while (bufferY != null) {
      currsegment = bufferY.remove(0);
      if (bufferY.isEmpty()) {
        bufferY = handler.read(root + "_sortedByY", ++page);
        noSortOperations++;
      }
      if (currsegment.isVertical()) {
        i = (int) (m*(currsegment.getX0()/range));
        if (i == m) i = m - 1;
        listA.get(i).add(currsegment);
        if ((listA.get(i).size()%B == 0)) noSortOperations += 2; //1 read, 1 write
      } else {
        if (currsegment.isHorizontal()) {
          i = (int) (m*(Math.min(currsegment.getX0(),currsegment.getX1())/range));
          j = (int) (m*(Math.max(currsegment.getX0(),currsegment.getX1())/range));
          for (int k = i+1; k < j; k++) {
            c = 0;
            while (c < listA.get(k).size()) {
              auxsegment = listA.get(k).get(c);
              if ((point = intersection(currsegment, auxsegment)) != null) {
                bw.write(String.format("(%f, %f)", point[0], point[1]));
                bw.newLine();
                intersectionsNumber++;
                c++;
              } else {
                listA.get(k).remove(c);
              }
            }
          }
        }
      }
    }
  }
  
  private float[] intersection(Segment s0, Segment s1) {
    float x1,x2,x3,x4,y1,y2,y3,y4;
    x1 = s0.getX0(); x2 = s0.getX1(); x3 = s1.getX0(); x4 = s1.getX1();
    y1 = s0.getY0(); y2 = s0.getY1(); y3 = s1.getY0(); y4 = s1.getY1();
    
    float d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
    if (d == 0) return null;
    
    float xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
    float yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;

    float[] p = {xi, yi};
    if (xi < Math.min(x1,x2) || xi > Math.max(x1,x2)) return null;
    if (xi < Math.min(x3,x4) || xi > Math.max(x3,x4)) return null;
    return p;
  }
  
  public long getTime() {
    return time;
  }
  
  public int getSortOperations() {
    return sortOperations;
  }
  
  public int getNoSortOperations() {
    return noSortOperations;
  }
  
  public int getIntersectionsNumber() {
    return intersectionsNumber;
  }
}
