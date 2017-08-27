package com.luolc.codejam.tool.io;

import com.luolc.codejam.tool.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author LuoLiangchen
 * @since 2017/4/30
 */
public final class OutputWriter {

  private final String path;

  public OutputWriter(String path) {
    this.path = "outputs" + File.separator + path;
    clearPreviousOutput();
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  private void clearPreviousOutput() {
    final int idx = path.lastIndexOf(File.separatorChar);
    final File dir = new File(path.substring(0, idx));
    if (dir.exists()) {
      if (!dir.isDirectory()) {
        dir.delete();
      }
    } else {
      dir.mkdirs();
    }

    final File out = new File(path);
    if (out.exists()) {
      out.delete();
    }
  }

  @SuppressWarnings("MalformedFormatString")
  public void write(String[] outputs) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
      int lineNo = 0;
      for (String output : outputs) {
        ++lineNo;
        final String content = String.format("Case #%d: %s\n", lineNo, output);
        bw.write(content);
      }
      Logger.i("Output success. Path: " + path);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
