package org.ums.statistics;

public class LoggerUtils {
  public static String buildQuery(String pQuery, final Object[] pQueryParams) {
    if (pQuery.contains("?")
        && pQueryParams.length > 0) {
      for (Object param : pQueryParams) {
        pQuery = pQuery.replaceFirst("\\?", isString(param) ? "'" + param.toString() + "'" : param.toString());
      }
    }

    return pQuery;
  }

  public static boolean isNumber(Object pObject) {
    return pObject instanceof Number;
  }

  public static boolean isString(Object pObject) {
    return pObject instanceof String;
  }
}
