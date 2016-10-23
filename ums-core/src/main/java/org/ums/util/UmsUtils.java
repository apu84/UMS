package org.ums.util;

public class UmsUtils {
  public static int FIRST = 1;

  public static String getNumberWithSuffix(final int pNumber) {
    String suffix = "";
    switch(pNumber) {
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

  public static int round(double d) {
    double dAbs = Math.abs(d);
    int i = (int) dAbs;
    double result = dAbs - (double) i;
    if(result < 0.5) {
      return d < 0 ? -i : i;
    }
    else {
      return d < 0 ? -(i + 1) : i + 1;
    }
  }
}
