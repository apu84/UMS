package org.ums.util;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.ums.enums.ExamType;

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

  public static String formatDate(Date date, String outputFormat) {
    Format formatter = new SimpleDateFormat(outputFormat);
    return formatter.format(date);
  }

  public static Date convertToDate(String dateStr, String dateFormat) {
    DateFormat formatter = new SimpleDateFormat(dateFormat);
    try {
      return formatter.parse(dateStr);
    } catch(Exception e) {
      return null;
    }
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

  public static String getWhereClause(String pFilter) {
    String where = "";
    try {
      String abc = "{\"rules\":" + pFilter + "}";
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(abc);

      JSONObject filters = (JSONObject) obj;
      JSONArray rules = (JSONArray) filters.get("rules");

      String groupOperation = "and";
      List<String> whereArray = new ArrayList<String>();

      for(Object rule_ : rules) {
        JSONObject rule = (JSONObject) rule_;
        String fieldName = (String) rule.get("fieldName");
        String fieldData = (String) rule.get("fieldValue");
        String op = (String) rule.get("operator");
        String fieldOperation = "";

        if(op.equalsIgnoreCase("eq")) {

          if(fieldName.contains("date"))
            fieldOperation = " = to_date('" + fieldData + "','dd-MM-YYYY')";
          else
            fieldOperation = " = '" + fieldData + "'";
        }
        else if(op.equalsIgnoreCase("ne"))
          fieldOperation = " != '" + fieldData + "'";

        else if(op.equalsIgnoreCase("lt")) {
          if(fieldName.contains("date"))
            fieldOperation = " < to_date('" + fieldData + "','dd-MM-YYYY')";
          else
            fieldOperation = " < '" + fieldData + "'";
        }
        else if(op.equalsIgnoreCase("gt")) {
          if(fieldName.contains("date"))
            fieldOperation = " > to_date('" + fieldData + "','dd-MM-YYYY')";
          else
            fieldOperation = " > '" + fieldData + "'";
        }
        else if(op.equalsIgnoreCase("le")) {
          if(fieldName.contains("date"))
            fieldOperation = " <= to_date('" + fieldData + "','dd-MM-YYYY')";
          else
            fieldOperation = " <= '" + fieldData + "'";
        }
        else if(op.equalsIgnoreCase("ge")) {
          if(fieldName.contains("date"))
            fieldOperation = " >= to_date('" + fieldData + "','dd-MM-YYYY')";
          else
            fieldOperation = " >= '" + fieldData + "'";
        }
        else if(op.equalsIgnoreCase("eqMonth")) {

          fieldOperation = " = " + fieldData + "";
        }
        else if(op.equalsIgnoreCase("eqYear")) {

          fieldOperation = " = '" + fieldData + "'";
        }
        else if(op.equalsIgnoreCase("nu"))
          fieldOperation = " = '' ";
        else if(op.equalsIgnoreCase("nn"))
          fieldOperation = " != '' ";
        else if(op.equalsIgnoreCase("in"))
          fieldOperation = " IN (" + fieldData + ")";
        else if(op.equalsIgnoreCase("ni"))
          fieldOperation = " NOT IN '" + fieldData + "";
        else if(op.equalsIgnoreCase("bw"))
          fieldOperation = " LIKE '" + fieldData + "%'";
        else if(op.equalsIgnoreCase("bn"))
          fieldOperation = " NOT LIKE '" + fieldData + "%'";
        else if(op.equalsIgnoreCase("ew"))
          fieldOperation = " LIKE '%" + fieldData + "'";
        else if(op.equalsIgnoreCase("en"))
          fieldOperation = " Not LIKE '%" + fieldData + "'";
        else if(op.equalsIgnoreCase("cn"))
          fieldOperation = " LIKE  lower('%" + fieldData + "%')";
        else if(op.equalsIgnoreCase("nc"))
          fieldOperation = " NOT LIKE '%" + fieldData + "%'";

        if(fieldOperation != "") {
          whereArray.add(" lower(" + fieldName + ") " + " " + fieldOperation);
        }
      }
      String[] array = whereArray.toArray(new String[whereArray.size()]);
      if(whereArray.size() > 0) {
        where += UmsUtils.join(" " + groupOperation + " ", array);
      }
      else {
        where = "";
      }
      where = where.equals("") ? "" : " Where " + where;
    } catch(Exception ex) {

    }
    return where;

  }

  public static String[] convertJsonStringToStringArray(String stringJsonArray, String propertyName) {
    List<String> strArr = new ArrayList<>();
    try {
      String abc = "{\"entries\":" + stringJsonArray+ "}";
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(abc);

      JSONObject filters = (JSONObject) obj;
      JSONArray rules = (JSONArray) filters.get("entries");

      for(Object rule_ : rules) {
        JSONObject rule = (JSONObject) rule_;
        strArr.add((String) rule.get(propertyName));
      }
    }
    catch (Exception ex){

    }


    return strArr.stream().toArray(String[]::new);
  }
}
