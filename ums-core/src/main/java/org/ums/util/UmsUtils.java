package org.ums.util;

import java.text.SimpleDateFormat;
import java.util.*;

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

  public static String formatDate(String date, String inputFormat, String outputFormat) {
    String fDate = "";
    try {
      SimpleDateFormat newDateFormat = new SimpleDateFormat(inputFormat);
      Date MyDate = newDateFormat.parse(date);
      newDateFormat.applyPattern(outputFormat);
      fDate = newDateFormat.format(MyDate);
    } catch(Exception ex) {

    }
    return fDate;
  }

  /**
   * Modify the time portion of a given date
   * 
   * @param inputDate Date for which the time need to be modified
   * @param hour New hour
   * @param minute New minute
   * @param second new Second
   * @return Modified Date-Time info for the given inputDate
   */
  public static Date updateTimeInfoOfDate(Date inputDate, int hour, int minute, int second, int milliSecond) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(inputDate);
    calendar.set(Calendar.HOUR_OF_DAY, hour);
    calendar.set(Calendar.MINUTE, minute);
    calendar.set(Calendar.SECOND, second);
    calendar.set(Calendar.MILLISECOND, milliSecond);
    return calendar.getTime();
  }

  /**
   * Update time of a given date so that it holds 23:59:59 as its date.
   * 
   * @param inputDate Date for which the time need to be modified
   * @return modified date with updated time information
   */
  public static Date modifyTimeToLastSecondOfTheClock(Date inputDate) {
    return updateTimeInfoOfDate(inputDate, 23, 59, 59, 59);
  }

  /**
   * Update time of a given date so that it holds 0:0:0 as its date.
   * 
   * @param inputDate Date for which the time need to be modified
   * @return modified date with updated time information
   */
  public static Date modifyTimeToZeroSecondOfTheClock(Date inputDate) {
    return updateTimeInfoOfDate(inputDate, 0, 0, 0, 0);
  }

  public static String join(String s, Object... a) {
    return a.length == 0 ? "" : a[0] + (a.length == 1 ? "" : s + join(s, Arrays.copyOfRange(a, 1, a.length)));
  }
}
