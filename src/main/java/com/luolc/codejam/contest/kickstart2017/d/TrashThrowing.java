package com.luolc.codejam.contest.kickstart2017.d;

import com.luolc.codejam.tool.io.Parser;
import com.luolc.codejam.tool.solution.Solution;
import com.luolc.codejam.tool.solution.Solver;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author LuoLiangchen
 * @since 2017/7/23
 */
final class TrashThrowing implements Parser<TrashThrowing.Input>, Solution<TrashThrowing.Input> {
  private static final String PATH =
      TrashThrowing.class.getPackage().getName().replaceAll("\\.", "/");

  private Solver<Input> solver =
      new Solver<>(PATH + "/" + TrashThrowing.class.getSimpleName(), "C");

  public static void main(String[] args) {
    new TrashThrowing().solve();
  }

  public void solve() {
    solver.solve(this, this);
//    solver.solve(this, this, true);
  }

  @Override
  public Input parseSingleCase(Scanner scanner) {
    Input input = new Input();
    input.N = scanner.nextInt();
    input.P = scanner.nextInt();
    input.H = scanner.nextInt();
    for (int i = 0; i < input.N; i++) {
      final Input.Coor coor = new Input.Coor();
      coor.x = scanner.nextInt();
      coor.y = scanner.nextInt();
      input.coors.add(coor);
    }
    return input;
  }

  @Override
  public String solveSingleCase(Input input) {
    return null;
  }

  private double[] getMaskInterval(Input input, double r, Input.Coor coor) {
    return null;
  }

  private boolean intersects(Input input, double r, Input.Coor coor, double a) {
    return true;
  }

  static final class Input {
    int N;
    int P;
    int H;
    List<Coor> coors = new LinkedList<>();

    static final class Coor {
      int x;
      int y;
    }
  }
}
