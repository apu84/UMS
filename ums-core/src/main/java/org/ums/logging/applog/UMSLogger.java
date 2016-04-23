package org.ums.logging.applog;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UMSLogger {
  private static final org.slf4j.Logger mLogger = LoggerFactory.getLogger(UMSLogger.class);

  private static LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

  public static List<Logger> getLoggers() {
    return loggerContext.getLoggerList();
  }

  public static void setLogger(final String pPackageName, final Level pLevel) {
    Logger rootLogger = loggerContext.getLogger(pPackageName);
    rootLogger.setLevel(pLevel);
    if (mLogger.isDebugEnabled()) {
      mLogger.debug("Setting log level for " + pPackageName + " to " + pLevel.levelStr);
    }
  }

  public static List<Level> getLogLevels() {
    List<Level> levels = new ArrayList<>();
    levels.add(Level.ALL);
    levels.add(Level.ERROR);
    levels.add(Level.DEBUG);
    levels.add(Level.INFO);
    levels.add(Level.TRACE);
    levels.add(Level.OFF);
    return levels;
  }
}
