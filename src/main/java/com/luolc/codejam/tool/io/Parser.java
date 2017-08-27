package com.luolc.codejam.tool.io;

import java.util.Scanner;

/**
 * @author LuoLiangchen
 * @since 2017/4/30
 */
public interface Parser<T> {
  T parseSingleCase(Scanner scanner);
}
