package com.luolc.codejam.contest.kickstart2017.b;

import com.luolc.codejam.tool.io.Parser;
import com.luolc.codejam.tool.solution.Solution;
import com.luolc.codejam.tool.solution.Solver;

import java.io.File;
import java.util.Scanner;

import static com.luolc.codejam.contest.kickstart2017.b.Contract.PATH;

/**
 * @author LuoLiangchen
 * @since 2017/5/7
 */
public final class ChristmasTree implements Parser<ChristmasTree.Input>, Solution<ChristmasTree.Input> {

  private Solver<Input> solver =
      new Solver<>(PATH + File.separator + ChristmasTree.class.getSimpleName(), "C");

  private static final boolean GREEN = true;

  public void solve() {
//    solver.solve(this, this);
    solver.solve(this, this, true);
  }

  @Override
  public Input parseSingleCase(Scanner scanner) {
    Input input = new Input();
    String[] args = scanner.nextLine().split(" ");
    input.row = Integer.valueOf(args[0]);
    input.column = Integer.valueOf(args[1]);
    input.count = Integer.valueOf(args[2]);
    input.picture = new boolean[input.row][input.column];
    for (int i = 0; i < input.row; i++) {
      String line = scanner.nextLine();
      for (int j = 0; j < line.length(); j++) {
        input.picture[i][j] = line.charAt(j) == '#';
      }
    }
    return input;
  }

  @Override
  public String solveSingleCase(Input input) {
    int[][][] dp = new int[input.row][input.column][input.column + 1];
    for (int i = 0; i < input.row; i++) {
      for (int j = 0; j < input.column; j++) {
        if (input.picture[i][j] == GREEN) {
          dp[i][j][1] = 1;
        }
      }
    }
    for (int i = 1; i < input.row; i++) {
      int[] left = toLeft(input.picture[i]);
      int[] right = toRight(input.picture[i]);
      for (int j = 0; j < input.column; j++) {
        for (int h = 2; h <= j + 1; h++) {
          if (j - h + 1 >= left[j] && j + h - 1 <= right[j] && dp[i - 1][j][h - 1] != 0) {
            dp[i][j][h] = dp[i - 1][j][h - 1] + 2 * h - 1;
          }
        }
      }
    }

    int[][][] lel = new int[input.row][input.column][input.count + 1];
    for (int i = 0; i < input.row; i++) {
      for (int j = 0; j < input.column; j++) {
        for (int h = 1; h <= j + 1; h++) {
          for (int jj = j - h + 1; jj <= j + h - 1 && jj < input.column; ++jj) {
            lel[i][jj][1] = Math.max(lel[i][jj][1], dp[i][j][h]);
          }
        }
      }
    }

    for (int i = 1; i < input.row; i++) {
      for (int j = 0; j < input.column; j++) {
        for (int h = 1; h <= j + 1 && i - h >= 0; h++) {
          for (int k = 2; k <= input.count; k++) {
            for (int jj = j - h + 1; jj <= j + h - 1 && jj < input.column; ++jj) {
              lel[i][jj][k] = Math.max(lel[i][jj][k], lel[i - h][j][k - 1] + dp[i][j][h]);
            }
          }
        }
      }
    }

    int ans = 0;
    for (int i = 0; i < input.row; i++) {
      for (int j = 0; j < input.column; j++) {
        ans = Math.max(ans, lel[i][j][input.count]);
      }
    }

    return String.valueOf(ans);
  }

  public String solveForSmall(Input input) {
//    int[][][] dp = new int[input.row][input.column][input.column + 1];
//    int ans = 0;
//    for (int i = 0; i < input.row; i++) {
//      for (int j = 0; j < input.column; j++) {
//        if (input.picture[i][j] == GREEN) {
//          dp[i][j][1] = 1;
//          ans = 1;
//        }
//      }
//    }
//    for (int i = 1; i < input.row; i++) {
//      int[] left = toLeft(input.picture[i]);
//      int[] right = toRight(input.picture[i]);
//      for (int j = 0; j < input.column; j++) {
//        for (int h = 2; h <= input.column; h++) {
//          if (j + 1 < h) continue;
//          if (j - h + 1 >= left[j] && j + h - 1 <= right[j] && dp[i - 1][j][h - 1] != 0) {
//            dp[i][j][h] = dp[i - 1][j][h - 1] + 2 * h - 1;
//            ans = Math.max(ans, dp[i][j][h]);
//          }
//        }
//      }
//    }
//    return String.valueOf(ans);
    return null;
  }

  public int[] toLeft(boolean[] line) {
    int[] ret = new int[line.length];
    int p = -1;
    for (int i = 0; i < line.length; i++) {
      if (line[i] != GREEN) {
        p = -1;
        ret[i] = -1;
      } else {
        if (p == -1) p = i;
        ret[i] = p;
      }
    }
    return ret;
  }

  public int[] toRight(boolean[] line) {
    int[] ret = new int[line.length];
    int p = -1;
    for (int i = line.length - 1; i >= 0; i--) {
      if (line[i] != GREEN) {
        p = -1;
        ret[i] = -1;
      } else {
        if (p == -1) p = i;
        ret[i] = p;
      }
    }
    return ret;
  }

  static class Input {
    int row;
    int column;
    int count;
    boolean[][] picture;
  }
}
