package main;

import java.util.ArrayList;
import java.util.List;

public class Test {
  public static void main(String[] args) {
    testMergeSort();
    //testIO();
  }

  //@SuppressWarnings("unused")
  private static void testMergeSort() {
    // create input
    int N = (int) Math.pow(2, 8);
    int B = 4096/Segment.SIZE;
    String root = "test";
    IOHandler handler = new IOHandler();
    
    int i = 0, j = 0;
    int currsize = 0;
    while (i < N) {
      List<Segment> slist = new ArrayList<Segment>();
      while (currsize < B && i < N) {
        slist.add(new Segment(0,1,1,2));
        currsize++;
        i++;
      }
      handler.write(slist, root, j);
      currsize = 0;
      j++;
    }
    
    // test mergesort
    Sorter sorter = new Sorter(root, N, 'x');
    sorter.mergeSort();
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
