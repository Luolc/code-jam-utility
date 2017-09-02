package com.luolc.codejam.algorithm;

import java.util.function.IntToLongFunction;

/**
 * @author LuoLiangchen
 * @since 2017/9/2
 */
public class BinarySearch {
  /**
   * Searches for the largest x in [left, right] that func(x) <= boundary. Returns {@code left - 1} if not exist.
   * @param left
   * @param right
   * @param func
   * @param boundary
   * @return
   */
  public static int upperBound(int left, int right, long boundary, IntToLongFunction func) {
    int l = left;
    int r = right;
    while (l < r) {
      final int mid = Math.floorDiv(l + r + 1, 2);
      final long converted = func.applyAsLong(mid);
      if (converted <= boundary) {
        l = mid;
      } else if (converted > boundary) {
        r = mid - 1;
      }
    }
    if (func.applyAsLong(l) > boundary) --l;
    return l;
  }

  /**
   * Searches for the smallest x in [left, right] that func(x) >= boundary. Returns {@code right + 1} if not exist.
   * @param left
   * @param right
   * @param func
   * @param boundary
   * @return
   */
  public static int lowerBound(int left, int right, long boundary, IntToLongFunction func) {
    int l = left;
    int r = right;
    while (l < r) {
      final int mid = Math.floorDiv(l + r, 2);
      final long converted = func.applyAsLong(mid);
      if (converted < boundary) {
        l = mid + 1;
      } else {
        r = mid;
      }
    }
    if (func.applyAsLong(l) < boundary) ++l;
    return l;
  }

  /**
   * Searches for the smallest x in [left, right] that func(x) > boundary. Returns {@code right + 1} if not exist.
   * @param left
   * @param right
   * @param boundary
   * @param func
   * @return
   */
  public static int largerThan(int left, int right, long boundary, IntToLongFunction func) {
    return upperBound(left, right, boundary, func) + 1;
  }

  /**
   * Searches for the largest x in [left, right] that func(x) < boundary. Returns {@code left - 1} if not exist.
   * @param left
   * @param right
   * @param boundary
   * @param func
   * @return
   */
  public static int lessThan(int left, int right, long boundary, IntToLongFunction func) {
    return lowerBound(left, right, boundary, func) - 1;
  }
}
