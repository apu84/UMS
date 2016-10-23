package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableLoggerEntry;
import org.ums.manager.LoggerEntryManager;

import java.util.Date;

public class PersistentLoggerEntry implements MutableLoggerEntry {
  private static LoggerEntryManager sLoggerEntryManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sLoggerEntryManager =
        applicationContext.getBean("loggerEntryManager", LoggerEntryManager.class);
  }

  private String mSql;
  private String mUserName;
  private long mExecutionTime;
  private Date mTimestamp;
  private Integer mId;

  public PersistentLoggerEntry() {}

  public PersistentLoggerEntry(final MutableLoggerEntry pLoggerEntry) {
    setId(pLoggerEntry.getId());
    setSql(pLoggerEntry.getSql());
    setUserName(pLoggerEntry.getUserName());
    setExecutionTime(pLoggerEntry.getExecutionTime());
    setTimestamp(pLoggerEntry.getTimestamp());
  }

  @Override
  public void setSql(String pSql) {
    mSql = pSql;
  }

  @Override
  public void setUserName(String pUserName) {
    mUserName = pUserName;
  }

  @Override
  public void setExecutionTime(long pExecutionTime) {
    mExecutionTime = pExecutionTime;
  }

  @Override
  public void setTimestamp(Date pTimestamp) {
    mTimestamp = pTimestamp;
  }

  @Override
  public String getSql() {
    return mSql;
  }

  @Override
  public String getUserName() {
    return mUserName;
  }

  @Override
  public long getExecutionTime() {
    return mExecutionTime;
  }

  @Override
  public Date getTimestamp() {
    return mTimestamp;
  }

  @Override
  public MutableLoggerEntry edit() throws Exception {
    return new PersistentLoggerEntry(this);
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public void commit(boolean update) throws Exception {
    if(update) {
      sLoggerEntryManager.update(this);
    }
    else {
      sLoggerEntryManager.delete(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sLoggerEntryManager.delete(this);
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }
}
