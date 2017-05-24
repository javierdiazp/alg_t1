package main;

public class Pair {
  private int index;
  private Segment segment;
  
  public Pair(Segment s, int i) {
    this.setSegment(s);
    this.setIndex(i);
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public Segment getSegment() {
    return segment;
  }

  public void setSegment(Segment segment) {
    this.segment = segment;
  }
}
