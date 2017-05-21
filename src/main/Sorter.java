package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sorter {
  private final int B = 4096/Segment.SIZE;
  private final int M = (int) (Runtime.getRuntime().maxMemory()/Segment.SIZE);
  private final int m = (int) (0.6*(M/B)); // use 60% of RAM to store elements

  private int n;
  private String root;
  private Comparator<Segment> cmp;
  
  public Sorter(String root, int N, char sortBy) {
    this.root = root;
    this.n = (int) Math.ceil((1.0*N/B));
    System.out.println("MaxMemory: " + Runtime.getRuntime().maxMemory());
    System.out.println("M: " + M);
    System.out.println("m: " + m);
    
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

    for (int i = 0; i < r; i++) {
      List<Segment> slist = new ArrayList<Segment>();
      
      for (int j = i*m; j < Math.min((i+1)*m, n); j++) {
        slist.addAll(handler.read(root, j));
      }
      slist.sort(cmp);
      handler.multipleWrite(slist, root + "_run0", i, B);
    }
    
    /* Part 2: Merge */
    int i = 0;
    String sep = "_";
    String path;
    do {
      List<List<Segment>> buffer = new ArrayList<List<Segment>>();
      for (int j = 0; j < r; j++) {
        path = root + sep + "run" + i + sep + j;
        buffer.add(handler.read(path, 0));
      }
      while (M == 0) { // FIXME
        // do something
      }
      // actualizo r
      i++;
    } while (r > m);
  }
  
  private final Comparator<Segment> cmpx = new Comparator<Segment>() {
    @Override
    public int compare(Segment s0, Segment s1) {
      return 0; // TODO
    }
  };
  
  private final Comparator<Segment> cmpy = new Comparator<Segment>() {
    @Override
    public int compare(Segment s0, Segment s1) {
      return 0; // TODO
    }
  };
}
