package main;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class IOHandler {
  private final String SEP = "_";
  
  public void write(List<Segment> slist, String root, int index) {
    RandomAccessFile file = null;
    String filename = root + SEP + index;
    Segment segment;
    
    try {
      file = new RandomAccessFile(filename, "rw");
      file.setLength(0);
      for (int i = 0; i < slist.size(); i++) {
        segment = slist.get(i);
        if (i != 0) file.writeChar(',');
        file.writeChar('(');
        file.writeFloat(segment.getX0());
        file.writeChar(',');
        file.writeFloat(segment.getY0());
        file.writeChar(',');
        file.writeFloat(segment.getX1());
        file.writeChar(',');
        file.writeFloat(segment.getY1());
        file.writeChar(')');
      }
    } catch (IOException e){
      e.printStackTrace();
    } finally {
      try {
        if (file != null) {
          file.close();
        }
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    } 
  }
  
  public List<Segment> read(String root, int index) {
    List<Segment> ans = new ArrayList<Segment>();
    RandomAccessFile file = null;
    String filename = root + SEP + index;
    float x0, y0, x1, y1;
    
    try {
      file = new RandomAccessFile(filename, "r");
      file.seek(0);
      while (true) {
        file.readChar();
        x0 = file.readFloat();
        file.readChar();
        y0 = file.readFloat();
        file.readChar();
        x1 = file.readFloat();
        file.readChar();
        y1 = file.readFloat();
        file.readChar();
        ans.add(new Segment(x0, y0, x1, y1));
        file.readChar();
      }
    } catch (EOFException e) {
    } catch (IOException e2) {
      return null;
    } finally {
      try {
        if (file != null)
          file.close();;
      } catch (IOException e3) {
        e3.printStackTrace();
      }
    }
    return ans;
  }
  
  public int multipleWrite(List<Segment> slist, String root, int B) {
    /* Return number of IO operations */
    int operations = 0;
    int currsize = 0;
    int init = 0;
    int j = 0;
    int k = 0;
    
    while (k < slist.size()) {
      while (currsize < B && k < slist.size()) {
        currsize++;
        k++;
      }
      write(slist.subList(init, k), root, j);
      operations++;
      init = k;
      currsize = 0;
      j++;
    }
    return operations;
  }
}
