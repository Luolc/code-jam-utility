package com.luolc.codejam.datastructure;

/**
 * @author LuoLiangchen
 * @since 2017/9/3
 */
public class Point3D {
  public final double x;

  public final double y;

  public final double z;

  public Point3D(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Point3D add(Point3D other) {
    return new Point3D(x + other.x, y + other.y, z + other.z);
  }

  public Point3D negate() {
    return new Point3D(-x, -y, -z);
  }

  public Point3D subtract(Point3D other) {
    return add(other.negate());
  }

  public Point3D multiply(double other) {
    return new Point3D(other * x, other * y, other * z);
  }

  public double dot(Point3D other) {
    return x * other.x + y * other.y + z * other.z;
  }

  public Point3D det(Point3D other) {
    return new Point3D(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
  }

  public double abs() {
    return Math.sqrt(dot(this));
  }

  public Point3D normalize() {
    return multiply(1 / abs());
  }
}
