package main;

public class Segment {
  public static final long SIZE = 28;
  
  private float x0;
  private float y0;
  private float x1;
  private float y1;
  
  public Segment(float x0, float y0, float x1, float y1) {
    this.setX0(x0);
    this.setY0(y0);
    this.setX1(x1);
    this.setY1(y1);
  }
  
  public float getX0() {
    return x0;
  }
  
  public void setX0(float x0) {
    this.x0 = x0;
  }
  
  public float getY0() {
    return y0;
  }
  
  public void setY0(float y0) {
    this.y0 = y0;
  }
  public float getX1() {
    return x1;
  }
  
  public void setX1(float x1) {
    this.x1 = x1;
  }
  
  public float getY1() {
    return y1;
  }
  
  public void setY1(float y1) {
    this.y1 = y1;
  }
  
  public String toString() {
    return String.format("(%f,%f,%f,%f)", getX0(), getY0(), getX1(), getY1());
  }
}
