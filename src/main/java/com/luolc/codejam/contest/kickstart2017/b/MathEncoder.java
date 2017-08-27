package com.luolc.codejam.contest.kickstart2017.b;

import com.luolc.codejam.tool.io.Parser;
import com.luolc.codejam.tool.solution.Solution;
import com.luolc.codejam.tool.solution.Solver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.luolc.codejam.contest.kickstart2017.b.Contract.PATH;

/**
 * @author LuoLiangchen
 * @since 2017/5/7
 */
final class MathEncoder implements Parser<MathEncoder.Input>, Solution<MathEncoder.Input> {

  private Solver<Input> solver =
      new Solver<>(PATH + File.separator + MathEncoder.class.getSimpleName(), "A");

  private static final long MOD = ((long) 1e9) + 7;

  public void solve() {
    solver.solve(this, this);
    solver.solve(this, this, true);
  }

  @Override
  public Input parseSingleCase(Scanner scanner) {
    Input input = new Input();
    int n = scanner.nextInt();
    for (int i = 0; i < n; i++) {
      input.nums.add(scanner.nextLong());
    }
    return input;
  }

  @Override
  public String solveSingleCase(Input input) {
    long ret = 0;
    int size = input.nums.size();
    for (int i = 0; i < size; i++) {
      for (int j = i + 1; j < size; j++) {
        ret += (input.nums.get(j) - input.nums.get(i)) * Math.pow(2, j - i - 1);
        ret %= MOD;
      }
    }
    return String.valueOf(ret);
  }

  static class Input {
    List<Long> nums = new ArrayList<>();
  }
}
