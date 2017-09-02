package com.luolc.codejam.contest.kickstart2017.e;

import com.luolc.codejam.tool.io.Parser;
import com.luolc.codejam.tool.solution.Solution;
import com.luolc.codejam.tool.solution.Solver;

import java.util.Scanner;

/**
 * @author LuoLiangchen
 * @since 2017/8/27
 */
final class CopyAndPaste implements Parser<CopyAndPaste.Input>, Solution<CopyAndPaste.Input> {
  // Utility classes could be found at https://github.com/Luolc/code-jam-utility
  private static final String PATH =
      CopyAndPaste.class.getPackage().getName().replaceAll("\\.", "/");

  private Solver<Input> solver =
      new Solver<>(PATH + "/" + CopyAndPaste.class.getSimpleName(), "A");

  public static void main(String[] args) {
    new CopyAndPaste().solve();
  }

  public void solve() {
//    solver.solve(this, this);
    solver.solve(this, this, true);
  }

  @Override
  public Input parseSingleCase(Scanner scanner) {
    Input input = new Input();
    input.str = scanner.nextLine();
    return input;
  }

  @Override
  public String solveSingleCase(Input input) {
    final int len = input.str.length();
    final int[][][] dp = new int[len + 1][len + 1][len + 1];
    for (int i = 0; i <= len; i++) {
      for (int j = 0; j <= len; j++) {
        for (int k = 0; k <= len; k++) {
          dp[i][j][k] = 101;
          dp[i][j][k] = 101;
        }
      }
    }
    dp[0][0][0] = 0;
    for (int i = 0; i < len; i++) {
      dp[i + 1][0][0] = Math.min(dp[i + 1][0][0], dp[i][0][0] + 1);
      for (int j = 0; j < i; j++) {
        for (int k = j + 1; k <= i; k++) {
          dp[i + 1][j][k] = Math.min(dp[i + 1][j][k], dp[i][j][k] + 1);
          if (input.str.substring(i).startsWith(input.str.substring(j, k))) {
            dp[i + k - j][j][k] = Math.min(dp[i + k - j][j][k], dp[i][j][k] + 1);
            dp[i + k - j][j][k] = Math.min(dp[i + k - j][j][k], dp[i][0][0] + 2);
            dp[i + k - j][0][0] = Math.min(dp[i + k - j][0][0], dp[i][j][k] + 1);
            dp[i + k - j][0][0] = Math.min(dp[i + k - j][0][0], dp[i][0][0] + 2);
          }
        }
      }
    }

    return String.valueOf(dp[len][0][0]);
  }

  private String solveSmall(Input input) {
    return String.valueOf(dfsSmall("", input.str, "", false));
  }

  private int dfsSmall(String current, String target, String clipboard, boolean justCopy) {
    if (current.equals(target)) return 0;
    int additional = Integer.MAX_VALUE;
    if (!clipboard.isEmpty() && target.startsWith(current + clipboard)) {
      additional = Math.min(additional, dfsSmall(current + clipboard, target, clipboard, false));
    } else {
      additional = Math.min(additional, dfsSmall(target.substring(0, current.length() + 1), target, clipboard, false));
    }
    if (!justCopy) {
      for (int i = 0; i < current.length(); i++) {
        for (int j = current.length(); j > i; j--) {
          if (target.startsWith(current + current.substring(i, j))) {
            additional = Math.min(additional, dfsSmall(current, target, current.substring(i, j), true));
          }
        }
      }
    }
    return additional + 1;
  }

  static final class Input {
    String str;
  }
}
