package org.ums.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

  public static String getPercentageString(int portion, int total) {
    return ((portion / total) * 100) + "%";
  }

  public static Map<String, Double> getGPAMap() {
    Map<String, Double> GPA_MAP = new HashMap<>();
    GPA_MAP.put("A+", 4.00);
    GPA_MAP.put("A", 3.75);
    GPA_MAP.put("A-", 3.50);
    GPA_MAP.put("B+", 3.25);
    GPA_MAP.put("B", 3.00);
    GPA_MAP.put("B-", 2.75);
    GPA_MAP.put("C+", 2.50);
    GPA_MAP.put("C", 2.25);
    GPA_MAP.put("D", 2.00);
    GPA_MAP.put("F", 0.00);
    return Collections.unmodifiableMap(GPA_MAP);
  }

  public static String nullConversion(String value) {
    return value == null ? "" : value;
  }

}
