package org.ums.statistics;


public interface QueryLogger {
  void log(final String pQuery, Object[] pQueryParams, final String pUserName);

  void log(final String pQuery, final String pUserName);
}
