package org.ums.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.MutableLoggerEntry;
import org.ums.manager.LoggerEntryManager;
import org.ums.persistent.model.PersistentLoggerEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class DBLogger implements QueryLogger {
  public static String QUERY_PARAM_PLACE_HOLDER = "\\?";
  @Autowired
  JdbcTemplate mJdbcTemplate;
  @Autowired
  LoggerEntryManager mLoggerEntryManager;

  final Queue<MutableLoggerEntry> mMutableLoggerEntries = new ConcurrentLinkedQueue<>();


  @Override
  @Async
  public void log(String pQuery, Object[] pQueryParams, String pUserName, final long pExecutionTime) {
    MutableLoggerEntry loggerEntry = new PersistentLoggerEntry();
    loggerEntry.setSql(buildQuery(pQuery, pQueryParams));
    loggerEntry.setUserName(pUserName);
    loggerEntry.setExecutionTime(pExecutionTime);
    loggerEntry.setTimestamp(new Date());
    mMutableLoggerEntries.add(loggerEntry);
  }

  @Override
  @Async
  public void log(String pQuery, String pUserName, final long pExecutionTime) {
    MutableLoggerEntry loggerEntry = new PersistentLoggerEntry();
    loggerEntry.setSql(pQuery);
    loggerEntry.setUserName(pUserName);
    loggerEntry.setExecutionTime(pExecutionTime);
    loggerEntry.setTimestamp(new Date());
    mMutableLoggerEntries.add(loggerEntry);
  }

  protected String buildQuery(String pQuery, final Object[] pQueryParams) {
    if (pQuery.contains(QUERY_PARAM_PLACE_HOLDER)
        && pQueryParams.length > 0) {
      for (Object param : pQueryParams) {
        pQuery = pQuery.replaceFirst(QUERY_PARAM_PLACE_HOLDER, isString(param) ? "'" + param.toString() + "'" : param.toString());
      }
    }

    return pQuery;
  }

  @Scheduled(fixedDelay = 30000)
  private void doLog() throws Exception {
    List<MutableLoggerEntry> mutableLoggerEntries = new ArrayList<>();
    synchronized (mMutableLoggerEntries) {
      MutableLoggerEntry ml;
      while ((ml = mMutableLoggerEntries.poll()) != null) {
        mutableLoggerEntries.add(ml);
      }
    }

    mLoggerEntryManager.update(mutableLoggerEntries);
  }

  private boolean isNumber(Object pObject) {
    return pObject instanceof Number;
  }

  private boolean isString(Object pObject) {
    return pObject instanceof String;
  }

}
