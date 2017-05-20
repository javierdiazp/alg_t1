package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sorter {
  private final long B = 4096;
  private final long M = Runtime.getRuntime().maxMemory();
  private final int m = (int) (M/B);
  private final String RUNSUFIX = "_run";
  
  @SuppressWarnings("unused") //FIXME
  private long N;
  private int n;
  private String root;
  private Comparator<String> cmp;
  
  public Sorter(String root, long N, char sortBy) {
    this.root = root;
    this.N = N;
    this.n = (int) (N/B);
    
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
    int r = n/m;
    IOHandler handler = new IOHandler();

    for (int i = 0; i < r; i++) {
      int currsize = 0;
      int maxsize = 32; // FIXME (int) B;
      int init = 0;
      List<String> slist = new ArrayList<String>();
      
      for (int j = i*m; j < (i+1)*m; j++) {
        slist.addAll(handler.read(root, j));
      }
      
      slist.sort(cmp);
      
      int j = 0;
      int k = 0;
      while (k < slist.size()) {
        while (currsize < maxsize && k < slist.size()) {
          currsize += 1 + slist.get(k).length();
          k++;
        }
        handler.write(slist.subList(init, k+1), root + RUNSUFIX + j, k);
        init = k+1;
        j++;
      }
    }
  }
  
  private final Comparator<String> cmpx = new Comparator<String>() {
    @Override
    public int compare(String s0, String s1) {
      return 0; // TODO
    }
  };
  
  private final Comparator<String> cmpy = new Comparator<String>() {
    @Override
    public int compare(String s0, String s1) {
      return 0; // TODO
    }
  };
}
