package com.luolc.codejam.datastructure;

import com.luolc.codejam.algorithm.DoubleComparator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author LuoLiangchen
 * @since 2017/9/3
 */
public class Circle {
  public final double r;

  public final Point o;

  private final DoubleComparator comparator;

  public Circle(double r, Point o) {
    this(r, o, new DoubleComparator(1e-6));
  }

  public Circle(double r, Point o, DoubleComparator comparator) {
    this.r = r;
    this.o = o;
    this.comparator = comparator;
  }

  public Point getPointAtAngle(double angle) {
    return new Point(o.x + r * Math.cos(angle), o.y + r * Math.sin(angle));
  }

  public List<Point> getIntersectionsWith(Circle other) {
    final double d = o.subtract(other.o).abs();
    if (comparator.compare(Math.abs(r - other.r), d) > 0 || comparator.compare(r + other.r, d) < 0) {
      return Collections.emptyList();
    } else {
      final double alpha = other.o.subtract(o).angle();
      final double theta = Math.acos((r * r + d * d - other.r * other.r) / (2 * r * d));
      return Arrays.asList(getPointAtAngle(alpha + theta), getPointAtAngle(alpha - theta));
    }
  }

  public boolean contains(Circle circle) {
    final double d = o.subtract(circle.o).abs();
    return comparator.compare(Math.abs(r - circle.r), d) > 0;
  }

  public boolean intersectsWith(Circle circle) {
    final double d = o.subtract(circle.o).abs();
    return comparator.compare(Math.abs(r - circle.r), d) < 0 && comparator.compare(r + circle.r, d) > 0;
  }
}
