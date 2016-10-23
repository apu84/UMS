package org.ums.statistics;

import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class TextLogger implements QueryLogger {
  private static final Logger mLogger = org.slf4j.LoggerFactory.getLogger(TextLogger.class);
  final Queue<String> mLoggerEntries = new ConcurrentLinkedQueue<>();

  @Override
  @Async
  public void log(String pQuery, Object[] pQueryParams, String pUserName, long pExecutionTime) {
    mLoggerEntries.add(String.format("SQL: %s ; Username: %s ; ET: %s ; ETS: %s ;",
        LoggerUtils.buildQuery(pQuery, pQueryParams), pUserName, pExecutionTime,
        (new Date()).toString()));
  }

  @Override
  @Async
  public void log(String pQuery, List<Object[]> pQueryParams, String pUserName, long pExecutionTime) {
    for(Object[] queryParams : pQueryParams) {
      mLoggerEntries.add(String.format("SQL: %s ; Username: %s ; ET: %s ; ETS: %s ;",
          LoggerUtils.buildQuery(pQuery, queryParams), pUserName, pExecutionTime,
          (new Date()).toString()));
    }

  }

  @Override
  @Async
  public void log(String pQuery, String pUserName, long pExecutionTime) {
    mLoggerEntries.add(String.format("SQL: %s ; Username: %s ; ET: %s ; ETS: %s ;", pQuery,
        pUserName, pExecutionTime, (new Date()).toString()));
  }

  @Override
  @Scheduled(fixedDelay = 15000, initialDelay = 60000)
  public void doLog() throws Exception {
    List<String> loggerEntries = new ArrayList<>();
    synchronized(mLoggerEntries) {
      String ml;
      while(!mLoggerEntries.isEmpty()) {
        loggerEntries.add(mLoggerEntries.poll());
      }
    }
    if(loggerEntries.size() > 0) {
      for(String log : loggerEntries) {
        mLogger.info(log);
      }
    }
  }
}
