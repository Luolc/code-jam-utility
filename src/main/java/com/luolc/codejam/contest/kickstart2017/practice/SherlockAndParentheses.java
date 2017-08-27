package com.luolc.codejam.contest.kickstart2017.practice;

import com.luolc.codejam.tool.io.Parser;
import com.luolc.codejam.tool.solution.Solution;
import com.luolc.codejam.tool.solution.Solver;

import java.io.File;
import java.util.Scanner;

import static com.luolc.codejam.contest.kickstart2017.practice.Contract.PATH;

/**
 * @author LuoLiangchen
 * @since 2017/5/1
 */
final class SherlockAndParentheses
    implements Parser<SherlockAndParentheses.Input>, Solution<SherlockAndParentheses.Input> {

  private Solver<Input> solver =
      new Solver<>(PATH + File.separator + SherlockAndParentheses.class.getSimpleName(), "C");

  void solve() {
    solver.solve(this, this);
    solver.solve(this, this, true);
  }

  @Override
  public Input parseSingleCase(Scanner scanner) {
    Input input = new Input();
    input.l = scanner.nextLong();
    input.r = scanner.nextLong();
    return input;
  }

  @Override
  public String solveSingleCase(Input input) {
    long n = Math.min(input.l, input.r);
    return String.valueOf(n * (n + 1) / 2);
  }

  static class Input {
    long l;

    long r;
  }
}
