package org.ums.statistics;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TextLogger implements QueryLogger {
  @Override
  public void log(String pQuery, Object[] pQueryParams, String pUserName, long pExecutionTime) {

  }

  @Override
  public void log(String pQuery, List<Object[]> pQueryParams, String pUserName, long pExecutionTime) {

  }

  @Override
  public void log(String pQuery, String pUserName, long pExecutionTime) {

  }

  @Override
  public void doLog() throws Exception {

  }
}
