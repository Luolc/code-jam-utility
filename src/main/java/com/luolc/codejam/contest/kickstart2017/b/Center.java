package com.luolc.codejam.contest.kickstart2017.b;

import com.luolc.codejam.tool.io.Parser;
import com.luolc.codejam.tool.solution.Solution;
import com.luolc.codejam.tool.solution.Solver;

import java.io.File;
import java.util.*;

import static com.luolc.codejam.contest.kickstart2017.b.Contract.PATH;

/**
 * @author LuoLiangchen
 * @since 2017/5/7
 */
final class Center implements Parser<Center.Input>, Solution<Center.Input> {

  private Solver<Input> solver =
      new Solver<>(PATH + File.separator + Center.class.getSimpleName(), "B");

  public void solve() {
    solver.solve(this, this);
//    solver.solve(this, this, true);
  }

  @Override
  public Input parseSingleCase(Scanner scanner) {
    Input input = new Input();
    int n = Integer.parseInt(scanner.nextLine());
    input.size = n;
    for (int i = 0; i < n; i++) {
      String[] args = scanner.nextLine().split(" ");
      Input.Node node = new Input.Node();
      node.x = Double.parseDouble(args[0]);
      node.y = Double.parseDouble(args[1]);
      input.nodes.add(node);
    }
    return input;
  }

  @Override
  public String solveSingleCase(Input input) {
    input.nodes.sort(new Comparator<Input.Node>() {
      @Override
      public int compare(Input.Node o1, Input.Node o2) {
        return o1.x < o2.x ? -1 : 1;
      }
    });
    double x = input.nodes.get(input.size / 2).x;
    input.nodes.sort(new Comparator<Input.Node>() {
      @Override
      public int compare(Input.Node o1, Input.Node o2) {
        return o1.y < o2.y ? -1 : 1;
      }
    });
    double y = input.nodes.get(input.size / 2).y;
    double ans = 0;
    for (Input.Node node : input.nodes) {
      ans += Math.abs(x - node.x) + Math.abs(y - node.y);
    }
    return String.valueOf(ans);
  }

  static class Input {
    int size;
    static class Node {
      double x;
      double y;
    }
    List<Node> nodes = new ArrayList<>();
  }
}
