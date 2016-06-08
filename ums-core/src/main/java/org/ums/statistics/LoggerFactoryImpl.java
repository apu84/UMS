package org.ums.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LoggerFactoryImpl implements LoggerFactory {
  @Autowired
  @Qualifier("dbLogger")
  private QueryLogger mDBLogger;

  @Autowired
  @Qualifier("textLogger")
  private QueryLogger mTextLogger;

  private String mLoggerType;

  @Override
  public QueryLogger getQueryLogger() {
    return mLoggerType.equalsIgnoreCase("DB") ? mDBLogger : mTextLogger;
  }

  public void setLoggerType(String pLoggerType) {
    mLoggerType = pLoggerType;
  }
}
