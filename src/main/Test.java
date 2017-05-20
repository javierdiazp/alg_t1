package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Test {
  public static void main(String[] args) throws IOException {
    Random rnd = new Random();
    String root = "input";
    String ext = ".txt";
    int n = 20;
    
    int x0, y0, x1, y1;
    String str;
    
    for (int i = 0; i < n; i++) {
      System.out.println("Processing " + i);
      FileWriter fw = new FileWriter(root+i+ext);
      BufferedWriter bw = new BufferedWriter(fw);
      
      x0 = rnd.nextInt(10);
      y0 = rnd.nextInt(10);
      x1 = rnd.nextInt(10);
      y1 = rnd.nextInt(10);
      str = String.format("(%d,%d,%d,%d)", x0, y0, x1, y1);
      bw.write(str);
      
      bw.write(",");
      
      x0 = rnd.nextInt(10);
      y0 = rnd.nextInt(10);
      x1 = rnd.nextInt(10);
      y1 = rnd.nextInt(10);
      str = String.format("(%d,%d,%d,%d)", x0, y0, x1, y1);
      bw.write(str);
      
      bw.write(",");
      
      x0 = rnd.nextInt(10);
      y0 = rnd.nextInt(10);
      x1 = rnd.nextInt(10);
      y1 = rnd.nextInt(10);
      str = String.format("(%d,%d,%d,%d)", x0, y0, x1, y1);
      bw.write(str);
      
      bw.close();
      fw.close();
    }
    Sorter st = new Sorter(root, 60, 'x');
    st.mergeSort();
    System.out.println("Done!");
  }
}
