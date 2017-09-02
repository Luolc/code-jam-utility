package com.luolc.codejam.contest.kickstart2017.e;

import com.luolc.codejam.algorithm.DoubleComparator;
import com.luolc.codejam.datastructure.Circle;
import com.luolc.codejam.datastructure.Point;
import com.luolc.codejam.datastructure.Point3D;
import com.luolc.codejam.tool.io.Parser;
import com.luolc.codejam.tool.solution.Solution;
import com.luolc.codejam.tool.solution.Solver;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author LuoLiangchen
 * @since 2017/8/27
 */
final class Blackhole implements Parser<Blackhole.Input>, Solution<Blackhole.Input> {
  // Utility classes could be found at https://github.com/Luolc/code-jam-utility
  private static final String PATH =
      Blackhole.class.getPackage().getName().replaceAll("\\.", "/");

  private Solver<Input> solver =
      new Solver<>(PATH + "/" + Blackhole.class.getSimpleName(), "C");

  private final DoubleComparator comparator = new DoubleComparator(1e-6);

  public static void main(String[] args) {
    new Blackhole().solve();
  }

  public void solve() {
//    solver.solve(this, this);
    solver.solve(this, this, true);
  }

  @Override
  public Input parseSingleCase(Scanner scanner) {
    Input input = new Input();
    Point3D[] points = new Point3D[3];
    for (int i = 0; i < points.length; i++) {
      final int[] args = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
      points[i] = new Point3D(args[0], args[1], args[2]);
    }
    input.points = points;
    return input;
  }

  @Override
  public String solveSingleCase(Input input) {
    final Point3D[] point3Ds = input.points;
    Point3D normalVector = point3Ds[1].subtract(point3Ds[0]).det(point3Ds[2].subtract(point3Ds[0]));
    if (comparator.compare(normalVector.abs(), 0d) == 0) {
      double dis = Math.max(point3Ds[1].subtract(point3Ds[0]).abs(), point3Ds[2].subtract(point3Ds[0]).abs());
      dis = Math.max(dis, point3Ds[2].subtract(point3Ds[1]).abs());
      return String.valueOf(dis / 6);
    }
    normalVector = normalVector.normalize();
    final Point3D e1 = point3Ds[1].subtract(point3Ds[0]).normalize();
    final Point3D e2 = normalVector.det(e1).normalize();
    final Point[] points = new Point[3];
    for (int i = 0; i < points.length; i++) {
      points[i] = new Point(point3Ds[i].dot(e1), point3Ds[i].dot(e2));
    }
    double l = 0;
    double r = 1000;
    while (comparator.compare(l, r) < 0) {
      final double mid = (l + r) / 2;
      if (isValidRadius(points, mid)) r = mid;
      else l = mid;
    }
    return String.valueOf(l);
  }

  private boolean isValidRadius(Point[] points, double radius) {
    int[][] args = {
        {1, 1, 5},
        {5, 1, 1},
        {1, 5, 1},
        {3, 3, 1},
        {1, 3, 3},
        {3, 1, 3},
    };
    for (int[] arg : args) {
      Circle[] circles = new Circle[3];
      for (int i = 0; i < 3; i++) {
        circles[i] = new Circle(arg[i] * radius, points[i], comparator);
      }
      if (hasCommonIntersection(circles)) return true;
    }
    return false;
  }

  private boolean hasCommonIntersection(Circle[] circles) {
    List<Point> intersections = new LinkedList<>();
    for (int i = 0; i < circles.length; i++) {
      for (int j = i + 1; j < circles.length; j++) {
        intersections.addAll(getIntersectionsOrTwoCenters(circles[i], circles[j]));
      }
    }
    for (Point intersection : intersections) {
      int count = 0;
      for (Circle circle : circles) {
        if (comparator.compare(intersection.subtract(circle.o).abs(), circle.r) <= 0) ++count;
      }
      if (count == circles.length) return true;
    }
    return false;
  }

  private List<Point> getIntersectionsOrTwoCenters(Circle c1, Circle c2) {
    if (c1.contains(c2) || c2.contains(c1)) {
      return Arrays.asList(c1.o, c2.o);
    } else {
      return c1.getIntersectionsWith(c2);
    }
  }

  static final class Input {
    Point3D[] points;
  }
}
