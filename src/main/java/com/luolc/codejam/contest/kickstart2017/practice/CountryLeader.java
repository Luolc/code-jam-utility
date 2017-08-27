package com.luolc.codejam.contest.kickstart2017.practice;

import com.luolc.codejam.tool.io.Parser;
import com.luolc.codejam.tool.solution.Solution;
import com.luolc.codejam.tool.solution.Solver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.luolc.codejam.contest.kickstart2017.practice.Contract.PATH;

/**
 * @author LuoLiangchen
 * @since 2017/5/1
 */
final class CountryLeader implements Parser<CountryLeader.Input>, Solution<CountryLeader.Input> {

  private Solver<Input> solver =
      new Solver<>(PATH + File.separator + CountryLeader.class.getSimpleName(), "A");

  void solve() {
    solver.solve(this, this);
    solver.solve(this, this, true);
  }

  @Override
  public Input parseSingleCase(Scanner scanner) {
    Input input = new Input();
    int n = Integer.parseInt(scanner.nextLine());
    for (int i = 0; i < n; i++) {
      input.names.add(scanner.nextLine());
    }
    return input;
  }

  @Override
  public String solveSingleCase(Input input) {
    String ans = "";
    int max = -1;
    for (String name : input.names) {
      int count = name.replaceAll(" ", "").chars()
          .boxed().collect(Collectors.toSet()).size();
      if (max == count) {
        if (name.compareTo(ans) < 0) {
          ans = name;
        }
      } else if (max < count) {
        max = count;
        ans = name;
      }
    }
    return ans;
  }

  static class Input {
    List<String> names = new ArrayList<>();
  }
}
