package org.ums.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateFormat extends SimpleDateFormat {
  private static final Logger mLogger = LoggerFactory.getLogger(DateFormat.class);

  public DateFormat(String pattern) {
    super(pattern);
  }

  @Override
  public Date parse(String source) {
    try {
      return super.parse(source);
    } catch(ParseException pe) {
      mLogger.error("Exception while parsing date, " + source, pe);
      throw new RuntimeException(pe);
    }
  }
}
