package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Sorter {
  private final int B = 4096/Segment.SIZE;
  private final int M = 524288/Segment.SIZE; //0.5 MB //(int) Runtime.getRuntime().maxMemory()/Segment.SIZE;
  private final int m = (int) M/B; 

  private int n;
  private char sortBy;
  private String root;
  private Comparator<Segment> cmp;
  
  private int operations;
  
  public Sorter(String root, int N, char sortBy) {
    this.root = root;
    this.sortBy = sortBy;
    this.n = (int) Math.ceil((1.0*N/B));
    this.operations = 0;
    
    switch (sortBy){
      case ('x'):
        this.cmp = cmpx;
        break;
      case ('y'): 
        this.cmp = cmpy;
        break;
      default:
        this.cmp = null;
        System.err.println("axis not supported");
        break;
    }
  }
  
  public void mergeSort() {
    /* Part 1: Create runs */
    int r = (int) Math.ceil(1.0*n/m);
    IOHandler handler = new IOHandler();
    List<Segment> slist = new ArrayList<Segment>();
    List <Segment> currlist;

    for (int i = 0; i < r; i++) {
      slist.clear();
      
      for (int j = i*m; j < Math.min((i+1)*m, n); j++) {
        currlist = handler.read(root, j);
        operations++;
        slist.addAll(currlist);
        /*
        if (sortBy == 'x') {
          for (Segment s: currlist) {
            if (s.isHorizontal()) {
              slist.add(new Segment(s.getX1(), s.getX0(), s.getY0(), s.getY1()));
            }
          }
        }
        */
        currlist.clear();
      }
      slist.sort(cmp);
      operations += handler.multipleWrite(slist, root + "_run0_" + i, B);
      slist.clear();
    }
    
    /* Part 2: Merge */
    int index;
    int runversion = 0;
    int outpage;
    int[] inpage;
    List<Segment> nextpage;
    List<Segment> outBuffer = new ArrayList<Segment>();
    List<List<Segment>> inBuffers = new ArrayList<List<Segment>>();
    Pair currpair;
    PriorityQueue<Pair> queue;
    String inpath;
    String outpath;
    String sep = "_";
    
    do {
      r = (int) Math.ceil(1.0*r/m);
      
      for (int i = 0; i < r; i++) {
        // Read runs i*m -> (i+1)*m - 1
        inBuffers.clear();
        outBuffer.clear();
        inpath = root + sep + "run" + runversion;
        if (r > m) {
          outpath = root + sep + "run" + (runversion+1) + sep + i; 
        } else {
          outpath = root + sep + "sortedBy" + Character.toUpperCase(sortBy);
        }
        for (int j = i*m; j < Math.min((i+1)*m, r); j++) {
          inBuffers.add(handler.read(inpath + sep + j, 0));
          operations++;
        }
        
        // Initialize auxiliar structures
        inpage = new int[inBuffers.size()];
        outpage = 0;
        queue = new PriorityQueue<Pair>(cmpp);
        for (int j = 0; j < inBuffers.size(); j++) {
          queue.add(new Pair(inBuffers.get(j).get(0), j));
        }
        
        // Merge runs
        while ((currpair = queue.poll()) != null) {
          // update inBuffers
          index = currpair.getIndex();
          inBuffers.get(index).remove(0);
          if (inBuffers.get(index).isEmpty()) {
            // read another page
            nextpage = handler.read(inpath + sep + index, ++inpage[index]);
            operations++;
            if (nextpage != null) {
              inBuffers.set(index, nextpage);
              queue.add(new Pair(inBuffers.get(index).get(0), index));
            }
          }
          else {
            queue.add(new Pair(inBuffers.get(index).get(0), index));
          }
          
          // update outBuffer
          outBuffer.add(currpair.getSegment());
          if (outBuffer.size() >= B) {
            handler.write(outBuffer, outpath, outpage++);
            operations++;
            outBuffer.clear();
          }
        }
        if (!outBuffer.isEmpty()) {
          handler.write(outBuffer, outpath, outpage++);
          operations++;
        }
      }
      runversion++;
    } while (r > m);
  }
  
  private final Comparator<Segment> cmpx = new Comparator<Segment>() {
    @Override
    public int compare(Segment s0, Segment s1) {
      return Float.compare(s0.getX0(), s1.getX0());
    }
  };
  
  private final Comparator<Segment> cmpy = new Comparator<Segment>() {
    @Override
    public int compare(Segment s0, Segment s1) {
      int ans =  -Float.compare(
          Math.max(s0.getY0(), s0.getY1()), 
          Math.max(s1.getY0(), s1.getY1()));
      if (ans == 0) {
        if (s0.isHorizontal() && s1.isVertical()) {
          return 1;
        }
        if (s0.isVertical() && s1.isHorizontal()) {
          return -1;
        }
      }
      return ans;
    }
  };
  
  private final Comparator<Pair> cmpp = new Comparator<Pair>() {
    @Override
    public int compare(Pair p0, Pair p1) {
      return cmp.compare(p0.getSegment(), p1.getSegment());
    }
  };
  
  public int getOperations() {
    return operations;
  }
}
