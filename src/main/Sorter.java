package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sorter {
  private final long B = 4096/Segment.SIZE;
  private final long M = Runtime.getRuntime().maxMemory()/Segment.SIZE;
  private final int m = (int) (M/B);

  private int n;
  private String root;
  private Comparator<Segment> cmp;
  
  public Sorter(String root, long N, char sortBy) {
    this.root = root;
    this.n = (int) Math.ceil((1.0*N/B));
    
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
    int r = (int) Math.ceil(1.0*n/m);
    IOHandler handler = new IOHandler();

    for (int i = 0; i < r; i++) {
      List<Segment> slist = new ArrayList<Segment>();
      
      for (int j = i*m; j < Math.min((i+1)*m, n); j++) {
        slist.addAll(handler.read(root, j));
      }
      slist.sort(cmp);
      handler.multipleWrite(slist, root, i, B);
    }
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
