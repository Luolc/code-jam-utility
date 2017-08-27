package com.luolc.codejam.tool;

/**
 * @author LuoLiangchen
 * @since 2017/5/1
 */
public final class Logger {

  private static boolean debuggable = false;

  public static void setDebuggable(boolean debuggable) {
    Logger.debuggable = debuggable;
  }

  public static void d(String message) {
    if (debuggable) {
      System.out.println("[DEBUG] " + message);
    }
  }

  public static void i(String message) {
    System.out.println("[INFO] " + message);
  }
}
