package com.luolc.codejam.tool.solution;

import com.luolc.codejam.tool.Logger;
import com.luolc.codejam.tool.io.InputGenerator;
import com.luolc.codejam.tool.io.OutputWriter;
import com.luolc.codejam.tool.io.Parser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author LuoLiangchen
 * @since 2017/4/30
 */
public final class Solver<T> {
  private static final ExecutorService sSolverService = Executors.newFixedThreadPool(4);

  private final String dir;

  private final String problemType;

  public Solver(String dir, String problemType) {
    this.dir = dir;
    this.problemType = problemType;
  }

  public void solve(Parser<T> parser, Solution<T> solution) {
    solve(parser, solution, false);
  }

  public void solve(Parser<T> parser, Solution<T> solution, boolean large) {
    printJvmSettings();

    final String caseScale = large ? "large" : "small";
    final String inputPath = dir + File.separator + problemType + "-" + caseScale + "-practice.in";
    final InputGenerator<T> inputGenerator = new InputGenerator<>(inputPath);
    final ArrayList<T> inputs = inputGenerator.parseInput(parser);
    Logger.i("Solving...");
    final long solveStartAt = System.currentTimeMillis();
    final String[] outputs = new String[inputs.size()];
    for (int i = 0; i < inputs.size(); i++) {
      sSolverService.execute(new SolutionRunnable(i, inputs.get(i), outputs, solution));
    }

    // hang
    boolean end;
    while (true) {
      end = true;
      for (String output : outputs) {
        end &= output != null;
      }
      if (end) break;
    }
    sSolverService.shutdown();

    final long solveEndAt = System.currentTimeMillis();
    final String solveTime = String.valueOf((solveEndAt - solveStartAt) / 1000.0) + "s";
    Logger.i("Solving completed. Time: " + solveTime);
    outputResults(caseScale, outputs);

    copySourceJavaFile();
  }

  private void outputResults(String caseScale, String[] outputs) {
    final String outputPath = dir + File.separator + problemType + "-" + caseScale + "-practice.out";
    final OutputWriter outputWriter = new OutputWriter(outputPath);
    outputWriter.write(outputs);
  }

  private void printJvmSettings() {
    final long maxMemory = Runtime.getRuntime().maxMemory();
    final long freeMemory = Runtime.getRuntime().freeMemory();
    final long totalMemory = Runtime.getRuntime().totalMemory();
    Logger.i("Max Memory=" + maxMemory / 1024 / 1024 + "M");
    Logger.i("Free Memory=" + freeMemory /1024 / 1024 + "M");
    Logger.i("Total Memory=" + totalMemory / 1024 / 1024 + "M");
  }

  private void copySourceJavaFile() {
    final String className = dir.substring(dir.lastIndexOf('/') + 1);
    final File source = new File("src/main/java", dir + ".java");
    final File copy = new File("outputs", dir + "/" + className + ".java");
    try {
      FileUtils.copyFile(source, copy);
    } catch (IOException ex) {
      throw new IllegalStateException(ex);
    }
  }

  private class SolutionRunnable implements Runnable {
    private int index;

    private T input;

    private String[] outputs;

    private Solution<T> solution;

    private SolutionRunnable(int index, T input, String[] outputs, Solution<T> solution) {
      this.index = index;
      this.input = input;
      this.outputs = outputs;
      this.solution = solution;
    }

    @Override
    public void run() {
      final long caseStartAt = System.currentTimeMillis();
      outputs[index] = solution.solveSingleCase(input);
      final long caseEndAt = System.currentTimeMillis();
      Logger.i("Case #" + (index + 1) + " completed. Time: "
          + String.valueOf((caseEndAt - caseStartAt) / 1000.0) + "s");
    }
  }
}
