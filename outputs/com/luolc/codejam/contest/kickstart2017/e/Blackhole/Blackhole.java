package com.luolc.codejam.contest.kickstart2017.e;

import com.luolc.codejam.tool.io.Parser;
import com.luolc.codejam.tool.solution.Solution;
import com.luolc.codejam.tool.solution.Solver;

import java.util.Arrays;
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

  public static void main(String[] args) {
    new Blackhole().solve();
  }

  public void solve() {
    solver.solve(this, this);
//    solver.solve(this, this, true);
  }

  @Override
  public Input parseSingleCase(Scanner scanner) {
    Input input = new Input();
    for (int i = 0; i < input.points.length; i++) {
      final int[] args = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
      input.points[i] = new Point();
      input.points[i].x = args[0];
      input.points[i].y = args[1];
      input.points[i].z = args[2];
    }
    return input;
  }

  @Override
  public String solveSingleCase(Input input) {
    return solveSmall(input);
  }

  private String solveSmall(Input input) {
    int min = Arrays.stream(input.points).mapToInt(p -> p.x).min().getAsInt();
    int max = Arrays.stream(input.points).mapToInt(p -> p.x).max().getAsInt();
    return String.valueOf((max - min) / 6.0);
  }

  static final class Input {
    Point[] points = new Point[3];
  }

  static final class Point {
    int x;
    int y;
    int z;
  }
}
