package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
  public static void main(String[] args) {
    mainTest();
    //createInput((int) Math.pow(2, 21), "input_u_0.75", 0.75f, 1000f);
    //testMergeSort(N, root, 'x');
    //testIO();
    System.out.println("Done!");
  }
  
  //@SuppressWarnings("unused")
  private static void mainTest() {
    int N;
    int i;
    float[] blist = {0.25f, 0.5f, 0.75f};
    float range = 1000f;
    String input, output;
    IntersectionFinder inter;
    
    for (i = 9; i < 22; i++) {
      for (float balance: blist) {
        System.out.println("Processing N = 2^" + i + ", Balance = " + balance);
        N = (int) Math.pow(2, i);
        
        input = "input_u_" + balance;
        output = String.format("out_%d_%f_unif_" + i, i, balance);
        inter = new IntersectionFinder(input, output, N, range);
        inter.distributionSweep();
        
        /*
        input = "input_n_" + balance;
        output = String.format("out_%i_&f_norm", i, balance);
        inter = new IntersectionFinder(input, output, N, 100f);
        inter.distributionSweep();
        */
      }
    }
  }
  
  @SuppressWarnings("unused")
  private static void createInput(int N, String root, float balance, float range) {
    int B = 4096/Segment.SIZE;
    IOHandler handler = new IOHandler();
    Random rnd = new Random();
    
    int i = 0, j = 0;
    int currsize = 0;
    float x0, x1, y0, y1;
    
    while (i < N) {
      List<Segment> slist = new ArrayList<Segment>();
      while (currsize < B && i < N) {
        if (rnd.nextFloat() < balance) { //horizontal
          x0 = rnd.nextFloat()*range;
          y0 = rnd.nextFloat()*range;
          x1 = rnd.nextFloat()*range;
          y1 = y0;
        } else { //vertical
          x0 = rnd.nextFloat()*range;
          y0 = rnd.nextFloat()*range;
          x1 = x0;
          y1 = rnd.nextFloat()*range;
        }
        slist.add(new Segment(x0, y0, x1, y1));
        currsize++;
        i++;
      }
      handler.write(slist, root, j);
      currsize = 0;
      j++;
    }
  }

  @SuppressWarnings("unused")
  private static void testMergeSort(int N, String root, char sortBy) {
    IOHandler handler = new IOHandler();
    Sorter sorter = new Sorter(root, N, sortBy);
    sorter.mergeSort();
    
    String path = root + "sortedBy" + Character.toUpperCase(sortBy) + "_0";
    List<Segment> slist = handler.read(path, 0);
    for (Segment s: slist) {
      System.out.println(s.toString());
    }
  }
  
  @SuppressWarnings("unused")
  private static void testIO() {
    int n = 146;
    List<Segment> slist = new ArrayList<Segment>();
    IOHandler handler = new IOHandler();
    
    for (int i = 0; i < n; i++) {
      slist.add(new Segment(0,1,1,2));
    }
    
    handler.write(slist, "test", 0);
    
    List<Segment> ans = handler.read("test", 0);
    for (Segment s: ans) {
      System.out.println(s.toString());
    }
  }
}
