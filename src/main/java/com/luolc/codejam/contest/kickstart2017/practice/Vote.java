package com.luolc.codejam.contest.kickstart2017.practice;

import com.luolc.codejam.tool.io.Parser;
import com.luolc.codejam.tool.solution.Solution;
import com.luolc.codejam.tool.solution.Solver;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

import static com.luolc.codejam.contest.kickstart2017.practice.Contract.PATH;

/**
 * @author LuoLiangchen
 * @since 2017/5/1
 */
final class Vote implements Parser<Vote.Input>, Solution<Vote.Input> {

  private Solver<Input> solver =
      new Solver<>(PATH + File.separator + Vote.class.getSimpleName(), "B");

  void solve() {
    solver.solve(this, this);
    solver.solve(this, this, true);
  }

  @Override
  public Input parseSingleCase(Scanner scanner) {
    Input input = new Input();
    input.n = scanner.nextInt();
    input.m = scanner.nextInt();
    return input;
  }

  @Override
  public String solveSingleCase(Input input) {
    BigInteger dp[][] = new BigInteger[input.n + 1][input.m + 1];
    for (BigInteger[] rows : dp) {
      for (int i = 0; i < rows.length; i++) {
        rows[i] = BigInteger.ZERO;
      }
    }
    for (int i = 0; i <= input.n; i++) {
      dp[i][0] = BigInteger.ONE;
    }
    for (int i = 1; i <= input.n; i++) {
      for (int j = 1; j < i && j <= input.m; j++) {
        dp[i][j] = dp[i - 1][j].add(dp[i][j - 1]);
      }
    }
    BigDecimal win = new BigDecimal(dp[input.n][input.m]);
    BigDecimal all = new BigDecimal(combination(input.n + input.m, input.m));
    int scale = Math.max(6, all.toString().length() + 1);
    return win.divide(all, scale, BigDecimal.ROUND_HALF_EVEN).toString();
  }

  private BigInteger combination(int base, int top) {
    BigInteger dividend = BigInteger.ONE;
    BigInteger divisor = BigInteger.ONE;
    for (int i = 0; i < top; i++) {
      dividend = dividend.multiply(BigInteger.valueOf(base - i));
      divisor = divisor.multiply(BigInteger.valueOf(top - i));
    }
    return dividend.divide(divisor);
  }

  static class Input {
    int n;

    int m;
  }
}
