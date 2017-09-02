package com.luolc.codejam.datastructure;

/**
 * @author LuoLiangchen
 * @since 2017/9/3
 */
public class Point {
  public final double x;

  public final double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Point add(Point other) {
    return new Point(x + other.x, y + other.y);
  }

  public Point negate() {
    return new Point(-x, -y);
  }

  public Point subtract(Point other) {
    return add(other.negate());
  }

  public Point multiply(double other) {
    return new Point(other * x, other * y);
  }

  public double dot(Point other) {
    return x * other.x + y * other.y;
  }

  public double det(Point other) {
    return x * other.y - y * other.x;
  }

  public double abs() {
    return Math.sqrt(dot(this));
  }

  public Point normalize() {
    return multiply(1 / abs());
  }

  public double angle() {
    return Math.atan2(y, x);
  }
}
