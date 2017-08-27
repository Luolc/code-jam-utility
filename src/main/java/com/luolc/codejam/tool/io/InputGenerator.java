package com.luolc.codejam.tool.io;

import com.luolc.codejam.tool.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author LuoLiangchen
 * @since 2017/4/30
 */
public final class InputGenerator<T> {

  private final String path;

  private final Scanner scanner;

  public InputGenerator(String path) {
    this.path = path;
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    final InputStream is = classLoader.getResourceAsStream(path);
    scanner = new Scanner(is);
  }

  public ArrayList<T> parseInput(Parser<T> parser) {
    final long startAt = System.currentTimeMillis();
    Logger.i("Start parsing input... Path: " + path);
    final int caseCount = Integer.parseInt(scanner.nextLine());
    final ArrayList<T> returnValue = new ArrayList<>(caseCount);
    for (int i = 0; i < caseCount; i++) {
      returnValue.add(parser.parseSingleCase(scanner));
    }
    final long endAt = System.currentTimeMillis();
    final String time = String.valueOf((endAt - startAt) / 1000.0) + "s";
    Logger.i("Parsing completed. Time: " + time);
    return returnValue;
  }
}
