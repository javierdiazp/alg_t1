package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IOHandler {
  private final String EXT = ".txt";
  private final String SEP = "_";
  private final String DIV = ",";
  
  public void write(List<String> slist, String root, int index) {
    FileWriter fw = null;
    BufferedWriter bw = null;
    String filename = root + SEP + index + EXT;
    String content = "";
    
    try {
      fw = new FileWriter(filename);
      bw = new BufferedWriter(fw);
      for (String s: slist) {
        content += DIV + s;
      }
      content = content.substring(1);
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
    String filename = root + SEP + index + EXT;
    
    try {
      fr = new FileReader(filename);
      br = new BufferedReader(fr);
      ans = Arrays.asList(br.readLine().split(",\\("));
      for (int i = 1; i < ans.size(); i++) {
        ans.set(i, "(" + ans.get(i));
      }
      for (String s: ans) {
        System.out.println(s);
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
