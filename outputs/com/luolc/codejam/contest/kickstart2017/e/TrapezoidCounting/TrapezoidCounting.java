package com.luolc.codejam.contest.kickstart2017.e;

import com.luolc.codejam.tool.io.Parser;
import com.luolc.codejam.tool.solution.Solution;
import com.luolc.codejam.tool.solution.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author LuoLiangchen
 * @since 2017/8/27
 */
final class TrapezoidCounting implements Parser<TrapezoidCounting.Input>, Solution<TrapezoidCounting.Input> {
  // Utility classes could be found at https://github.com/Luolc/code-jam-utility
  private static final String PATH =
      TrapezoidCounting.class.getPackage().getName().replaceAll("\\.", "/");

  private Solver<Input> solver =
      new Solver<>(PATH + "/" + TrapezoidCounting.class.getSimpleName(), "B");

  public static void main(String[] args) {
    new TrapezoidCounting().solve();
  }

  public void solve() {
//    solver.solve(this, this);
    solver.solve(this, this, true);
  }

  @Override
  public Input parseSingleCase(Scanner scanner) {
    Input input = new Input();
    scanner.nextLine();
    long[] nums = Arrays.stream(scanner.nextLine().split(" ")).mapToLong(Long::parseLong).toArray();
    Arrays.sort(nums);
    Num cur = null;
    for (long num : nums) {
      if (cur == null || cur.val != num) {
        cur = new Num();
        cur.val = num;
        cur.count = 1;
        input.nums.add(cur);
      } else {
        cur.count++;
      }
    }
    input.sum = new long[input.nums.size() + 1];
    input.sum[0] = 0;
    for (int i = 0; i < input.nums.size(); i++) {
      input.sum[i + 1] = input.sum[i] + input.nums.get(i).count;
    }
    return input;
  }

  @Override
  public String solveSingleCase(Input input) {
    long ans = 0;
    for (int i = 0; i < input.nums.size(); i++) {
      final Num side = input.nums.get(i);
      if (side.count == 1) continue;

      long arg = C(side.count, 2);
      for (int j = 1; j < input.nums.size(); j++) {
        if (i == j) continue;
        final Num down = input.nums.get(j);
        int l = 0;
        int r = j - 1;
        final long boundary = down.val - 2 * side.val;
        while (l < r) {
          final int mid = Math.floorDiv(l + r, 2);
          if (input.nums.get(mid).val <= boundary) l = mid + 1;
          else r = mid;
        }
        final Num up = input.nums.get(l);
        if (up.val + 2 * side.val <= down.val) continue;
        long sum = input.sum[j] - input.sum[l];
        if (i >= l && i < j) sum -= side.count;
        ans += arg * down.count * sum;

//        for (int k = 0; k < input.nums.size() && k < j; k++) {
//          if (i == k) continue;
//          if (input.nums.get(k).val + 2 * side.val <= down.val) continue;
//          ans += arg * down.count * input.nums.get(k).count;
//        }
      }

      if (side.count >= 3) {
        arg = C(side.count, 3);
        for (int j = 0; j < input.nums.size() && 3 * side.val > input.nums.get(j).val; j++) {
          if (i == j) continue;
          ans += arg * input.nums.get(j).count;
        }
      }
    }
    return String.valueOf(ans);
  }

  private long C(long down, int up) {
    long ans = 1;
    if (up == 2) {
      ans = down * (down - 1) / 2;
    } else if (up == 3) {
      ans = down * (down - 1) * (down - 2) / 6;
    }
    return ans;
  }

  static final class Input {
    ArrayList<Num> nums = new ArrayList<>();
    long[] sum;
  }

  static final class Num {
    long val;
    long count;
  }
}
