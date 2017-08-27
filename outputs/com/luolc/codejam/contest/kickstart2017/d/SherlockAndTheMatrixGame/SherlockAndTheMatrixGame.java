package com.luolc.codejam.contest.kickstart2017.d;

import com.google.common.collect.TreeMultiset;
import com.luolc.codejam.datastructure.Treap;
import com.luolc.codejam.tool.io.Parser;
import com.luolc.codejam.tool.solution.Solution;
import com.luolc.codejam.tool.solution.Solver;

import java.util.*;

/**
 * @author LuoLiangchen
 * @since 2017/7/18
 */
final class SherlockAndTheMatrixGame implements Parser<SherlockAndTheMatrixGame.Input>, Solution<SherlockAndTheMatrixGame.Input> {
  private static final String PATH =
      SherlockAndTheMatrixGame.class.getPackage().getName().replaceAll("\\.", "/");

  private Solver<Input> solver =
      new Solver<>(PATH + "/" + SherlockAndTheMatrixGame.class.getSimpleName(), "B");

  public static void main(String[] args) {
    new SherlockAndTheMatrixGame().solve();
  }

  public void solve() {
//    solver.solve(this, this);
    solver.solve(this, this, true);
  }

  @Override
  public Input parseSingleCase(Scanner scanner) {
    Input input = new Input();
    int[] args = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::valueOf).toArray();
    input.len = args[0];
    input.K = args[1];
    input.A = new int[input.len];
    input.B = new int[input.len];
    input.A[0] = args[2];
    input.B[0] = args[3];
    final int[] r = new int[input.len];
    final int[] s = new int[input.len];
    r[0] = 0;
    s[0] = 0;
    final int C = args[4];
    final int D = args[5];
    final int E1 = args[6];
    final int E2 = args[7];
    final int F = args[8];
    for (int i = 1; i < input.len; i++) {
      r[i] = (C * r[i - 1] + D * s[i - 1] + E1) % 2;
      s[i] = (D * r[i - 1] + C * s[i - 1] + E2) % 2;
    }
    for (int i = 1; i < input.len; i++) {
      input.A[i] = (C * input.A[i - 1] + D * input.B[i - 1] + E1) % F;
      input.B[i] = (D * input.A[i - 1] + C * input.B[i - 1] + E2) % F;
    }
    for (int i = 0; i < input.len; i++) {
      if (r[i] == 1) input.A[i] = -input.A[i];
      if (s[i] == 1) input.B[i] = -input.B[i];
    }
    return input;
  }

  @Override
  public String solveSingleCase(Input input) {
//    return solveSmall(input);
    return solveLarge(input);
  }

  private String solveLarge(Input input) {
    final long[] sumA = getSumArray(input, input.A);
    final long[] sumB = getSumArray(input, input.B);
    long l = ((long) Integer.MIN_VALUE) * Integer.MAX_VALUE;
    long r = ((long) Integer.MAX_VALUE) * Integer.MAX_VALUE;
    while (l < r) {
      final long mid = Math.floorDiv(l + r + 1, 2);
      final long count = countOfResultNoLessThan(mid, sumA, sumB);
      if (count < input.K) {
        r = mid - 1;
      } else {
        l = mid;
      }
    }
    return String.valueOf(l);
  }

  private String solveSmall(Input input) {
    final long[] sumA = getSumArray(input, input.A);
    final long[] sumB = getSumArray(input, input.B);
    final PriorityQueue<Long> queue = new PriorityQueue<>(input.K);
    for (long i : sumA) {
      for (long j : sumB) {
        if (queue.size() < input.K) {
          queue.add(i * j);
        } else if (i * j > queue.peek()) {
          queue.poll();
          queue.add(i * j);
        }
      }
    }
    return String.valueOf(queue.peek());
  }

  private long countOfResultNoLessThan(long boundary, long[] sumA, long[] sumB) {
    long count = 0;
    for (long a : sumA) {
      if (a == 0) {
        if (boundary <= 0) count += sumB.length;
      } else if (a > 0) {
        int l = 0;
        int r = sumB.length;
        while (l < r) {
          final int mid = Math.floorDiv(l + r, 2);
          if (sumB[mid] * a >= boundary) r = mid;
          else l = mid + 1;
        }
        count += sumB.length - l;
      } else {
        int l = 0;
        int r = sumB.length;
        while (l < r) {
          final int mid = Math.floorDiv(l + r, 2);
          if (sumB[mid] * a >= boundary) l = mid + 1;
          else r = mid;
        }
        count += l;
      }
    }
    return count;
  }

  private long[] getSumArray(Input input, int[] array) {
    int len = array.length;
    long[] prefixSums = new long[len + 1];
    prefixSums[0] = 0;
    for (int i = 1; i <= len; i++) {
      prefixSums[i] = prefixSums[i - 1] + array[i - 1];
    }

    final ArrayList<Long> ret;
    if (2 * input.K < (long) len * (len + 1) / 2) {
      ret = new ArrayList<>(2 * input.K);
      int boundary = getBoundary(input, prefixSums);
      ret.addAll(sumsNoLargerThan(input, boundary, prefixSums));

      for (int i = 0; i < prefixSums.length; i++) {
        prefixSums[i] = -prefixSums[i];
      }
      boundary = getBoundary(input, prefixSums);
      for (Long sum : sumsNoLargerThan(input, boundary, prefixSums)) {
        ret.add(-sum);
      }
    } else {
      ret = new ArrayList<>(len * (len + 1) / 2);
      for (int i = 0; i < len; i++) {
        for (int j = i + 1; j <= len; j++) {
          ret.add(prefixSums[j] - prefixSums[i]);
        }
      }
    }
    return ret.stream().mapToLong(Long::longValue).sorted().toArray();
  }

  private int getBoundary(Input input, long[] prefixSums) {
    int l = Integer.MIN_VALUE;
    int r = Integer.MAX_VALUE;
    while (l < r) {
      final int mid = Math.floorDiv(l + r, 2);
      final long count = countOfSumsNoLargerThan(mid, prefixSums);
      if (count < input.K) l = mid + 1;
      else r = mid;
    }
    return l;
  }

  private long countOfSumsNoLargerThan(int boundary, long[] prefixSums) {
    final int len = prefixSums.length - 1;
    final Treap<Long> treap = new Treap<>();
    long count = 0;
    for (int i = len; i > 0; i--) {
      treap.insert(prefixSums[i]);
      count += treap.rankInclusive(prefixSums[i - 1] + boundary);
    }
    return count;
  }

  private List<Long> sumsNoLargerThan(Input input, int boundary, long[] prefixSums) {
    final int len = prefixSums.length - 1;
    final TreeMultiset<Long> set = TreeMultiset.create(((o1, o2) -> -o1.compareTo(o2)));
    final ArrayList<Long> ret = new ArrayList<>(input.K);
    for (int i = 0; i < len; i++) {
      set.add(prefixSums[i]);
      for (Long sum : set) {
        if (ret.size() == input.K) break;
        if (prefixSums[i + 1] - sum >= boundary) break;
        ret.add(prefixSums[i + 1] - sum);
      }
    }
    while (ret.size() != input.K) ret.add((long) boundary);
    return ret;
  }

  static final class Input {
    int len;
    int K;
    int[] A;
    int[] B;
  }
}
