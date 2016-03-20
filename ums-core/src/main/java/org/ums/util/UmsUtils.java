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
      case 3:
        suffix = "rd";
        break;
      default:
        suffix = "th";
        break;
    }
    return pNumber + "" + suffix;
  }
}
