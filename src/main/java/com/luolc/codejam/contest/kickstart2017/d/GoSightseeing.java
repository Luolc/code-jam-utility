package com.luolc.codejam.contest.kickstart2017.d;

import com.luolc.codejam.tool.io.Parser;
import com.luolc.codejam.tool.solution.Solution;
import com.luolc.codejam.tool.solution.Solver;

import java.io.File;
import java.util.Scanner;

/**
 * @author LuoLiangchen
 * @since 2017/7/16
 */
final class GoSightseeing implements Parser<GoSightseeing.Input>, Solution<GoSightseeing.Input> {
  private static final String PATH =
      GoSightseeing.class.getPackage().getName().replaceAll("\\.", "/");

  private Solver<Input> solver =
      new Solver<>(PATH + File.separator + GoSightseeing.class.getSimpleName(), "A");

  public static void main(String[] args) {
    new GoSightseeing().solve();
  }

  public void solve() {
//    solver.solve(this, this);
    solver.solve(this, this, true);
  }

  @Override
  public Input parseSingleCase(Scanner scanner) {
    Input input = new Input();
    String[] args = scanner.nextLine().split(" ");
    input.cityCount = Integer.valueOf(args[0]);
    input.playTime = Integer.valueOf(args[1]);
    input.deadline = Integer.valueOf(args[2]);
    input.start = new int[input.cityCount + 1];
    input.frequency = new int[input.cityCount + 1];
    input.duration = new int[input.cityCount + 1];
    String[] line;
    for (int i = 2; i <= input.cityCount; i++) {
      line = scanner.nextLine().split(" ");
      input.start[i] = Integer.valueOf(line[0]);
      input.frequency[i] = Integer.valueOf(line[1]);
      input.duration[i] = Integer.valueOf(line[2]);
    }
    return input;
  }

  @Override
  public String solveSingleCase(Input input) {
    final long[][] dp = new long[input.cityCount + 1][input.cityCount + 1];
    for (int i = 2; i <= input.cityCount; i++) {
      for (int j = 0; j < i; j++) {
        dp[i][j] = Integer.MAX_VALUE;
        if (i - 1 > j) {
          dp[i][j] = Math.min(dp[i][j],
              waitBusAndGo(dp[i - 1][j], input.start[i], input.frequency[i], input.duration[i]));
        }
        if (j - 1 >= 0) {
          dp[i][j] = Math.min(dp[i][j],
              waitBusAndGo(dp[i - 1][j - 1] + input.playTime, input.start[i], input.frequency[i], input.duration[i]));
        }
      }
    }
    String ans = "IMPOSSIBLE";
    for (int i = input.cityCount - 1; i >=0; i--) {
      if (dp[input.cityCount][i] <= input.deadline) {
        ans = String.valueOf(i);
        break;
      }
    }
    return ans;
  }

  private long waitBusAndGo(long now, long start, long frequency, long duration) {
    if (now <= start) {
      return start + duration;
    } else {
      long v = (now - start) / frequency;
      start += v * frequency;
      if (now == start) return start + duration;
      else return start + frequency + duration;
    }
  }

  static class Input {
    int cityCount;
    int playTime;
    int deadline;
    int[] start;
    int[] frequency;
    int[] duration;
  }
}
