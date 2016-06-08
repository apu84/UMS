package org.ums.statistics;

public class LoggerUtils {
  public static String QUERY_PARAM_PLACE_HOLDER = "\\?";

  public static String buildQuery(String pQuery, final Object[] pQueryParams) {
    if (pQuery.contains(QUERY_PARAM_PLACE_HOLDER)
        && pQueryParams.length > 0) {
      for (Object param : pQueryParams) {
        pQuery = pQuery.replaceFirst(QUERY_PARAM_PLACE_HOLDER, isString(param) ? "'" + param.toString() + "'" : param.toString());
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
