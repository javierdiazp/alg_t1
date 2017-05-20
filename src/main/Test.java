package main;

import java.util.ArrayList;
import java.util.List;

public class Test {
  public static void main(String[] args) {
    /*
    double a = 3.5e-2;
    String b = "0.035";
    System.out.println((byte) a);
    System.out.println(Byte.valueOf(b));
    */
    
    int n = 10;
    List<String> slist = new ArrayList<String>();
    for (int i = 0; i < n; i++) {
      slist.add(String.format("(%d,%d,%d,%d)",i,i+1,i+1,i+2));
    }
    IOHandler handler = new IOHandler();
    handler.write(slist, "test", 0);
    System.out.println("Done!");
    
    
  }
}
