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
  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  LoggerEntryManager mLoggerEntryManager;

  final Queue<MutableLoggerEntry> mMutableLoggerEntries = new ConcurrentLinkedQueue<>();


  @Override
  @Async
  public void log(String pQuery, Object[] pQueryParams, String pUserName, final long pExecutionTime) {
    MutableLoggerEntry loggerEntry = new PersistentLoggerEntry();
    loggerEntry.setSql(LoggerUtils.buildQuery(pQuery, pQueryParams));
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

  @Override
  @Async
  public void log(String pQuery, List<Object[]> pQueryParams, String pUserName, long pExecutionTime) {
    for (Object[] params : pQueryParams) {
      this.log(pQuery, params, pUserName, pExecutionTime);
    }
  }


  @Scheduled(fixedDelay = 30000, initialDelay = 60000)
  public void doLog() throws Exception {
    List<MutableLoggerEntry> mutableLoggerEntries = new ArrayList<>();
    synchronized (mMutableLoggerEntries) {
      MutableLoggerEntry ml;
      while (!mMutableLoggerEntries.isEmpty()) {
        mutableLoggerEntries.add(mMutableLoggerEntries.poll());
      }
    }
    if (mutableLoggerEntries.size() > 0) {
      mLoggerEntryManager.create(mutableLoggerEntries);
    }
  }
}
