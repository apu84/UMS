package org.ums.util;


public class UmsUtils {
  public static int FIRST = 1;
  public static String getNumberWithSuffix(final int pNumber) {
    String suffix = "";
    switch (pNumber) {
      case 1:
        suffix = "st";
        break;
      case 2:
        suffix = "nd";
        break;
      default:
        suffix = "rd";
        break;
    }
    return pNumber + "" + suffix;
  }
}
