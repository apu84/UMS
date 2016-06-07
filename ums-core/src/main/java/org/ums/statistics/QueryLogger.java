package org.ums.statistics;


public interface QueryLogger {
  void log(final String pQuery, Object[] pQueryParams, final String pUserName, final long pExecutionTime);

  void log(final String pQuery, final String pUserName, final long pExecutionTime);
}
