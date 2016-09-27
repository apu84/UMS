package org.ums.statistics;

import java.util.regex.Pattern;

public class LoggerUtils {
  static Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");
  public static String buildQuery(String pQuery, final Object[] pQueryParams) {
    if (pQuery.contains("?")
        && pQueryParams.length > 0) {
      for (Object param : pQueryParams) {

        pQuery = pQuery.replaceFirst("\\?", isString(param)
            ? "'" + escapeSpecialRegexChars(param.toString()) + "'"
            : param.toString());
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

  private static String escapeSpecialRegexChars(String str) {
    return SPECIAL_REGEX_CHARS.matcher(str).replaceAll("\\\\$0");
  }
}
