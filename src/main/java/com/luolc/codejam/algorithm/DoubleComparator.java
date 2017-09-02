package com.luolc.codejam.algorithm;

import java.util.Comparator;

/**
 * @author LuoLiangchen
 * @since 2017/9/3
 */
public class DoubleComparator implements Comparator<Double> {
  private final double eps;

  public DoubleComparator(double eps) {
    this.eps = eps;
  }

  @Override
  public int compare(Double o1, Double o2) {
    if (o1 < o2 - eps) return -1;
    else if (o1 > o2 + eps) return 1;
    else return 0;
  }
}
