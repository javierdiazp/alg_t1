package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOHandler {
  private final String EXT = ".txt";
  private final String SEP = ",";
  
  // TODO Arreglar read para soportar formato s,s,s....
  public void write(List<String> slist, String root, int index) {
    FileWriter fw = null;
    BufferedWriter bw = null;
    String filename = root + "_" + index + EXT;
    String content = "";
    
    try {
      fw = new FileWriter(filename);
      bw = new BufferedWriter(fw);
      for (String s: slist) {
        content += SEP + s;
      }
      content = content.substring(SEP.length());
      bw.write(content);
    } catch (IOException e){
      e.printStackTrace();
    } finally {
      try {
        if (bw != null) {
          bw.close();
        }
        if (fw != null) {
          fw.close();
        }
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    } 
  }
  
  public List<String> read(String root, int index) {
    List<String> ans = new ArrayList<String>();
    FileReader fr = null;
    BufferedReader br = null;
    String filename = root + index + EXT;
    String currentline;
    
    try {
      fr = new FileReader(filename);
      br = new BufferedReader(fr);
      while ((currentline = br.readLine()) != null) {
        ans.add(currentline);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null)
          br.close();
        if (fr != null)
          fr.close();
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    }
    return ans;
  }
}
