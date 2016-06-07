package org.ums.statistics;


import java.util.List;

public interface QueryLogger {
  void log(final String pQuery, Object[] pQueryParams, final String pUserName, final long pExecutionTime);

  void log(final String pQuery, List<Object[]> pQueryParams, final String pUserName, final long pExecutionTime);

  void log(final String pQuery, final String pUserName, final long pExecutionTime);

  void doLog() throws Exception;
}
