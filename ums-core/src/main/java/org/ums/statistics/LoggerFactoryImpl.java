package org.ums.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class LoggerFactoryImpl implements LoggerFactory {
  @Autowired
  @Qualifier("dbLogger")
  @Lazy
  private QueryLogger mDBLogger;

  @Autowired
  @Qualifier("textLogger")
  @Lazy
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
