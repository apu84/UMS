package org.ums.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTemplateFactoryImpl implements JdbcTemplateFactory {
  private boolean mLoggingEnabled;

  @Autowired
  @Qualifier("jdbcTemplate")
  JdbcTemplate mJdbcTemplate;

  @Autowired
  @Qualifier("umsJdbcTemplate")
  UMSJdbcTemplate mUMSJdbcTemplate;

  @Autowired
  @Qualifier("lmsJdbcTemplate")
  JdbcTemplate mLMSJdbcTemplate;

  @Override
  public JdbcTemplate getJdbcTemplate() {
    return mLoggingEnabled ? mUMSJdbcTemplate : mJdbcTemplate;
  }

  @Override
  public JdbcTemplate getLmsJdbcTemplate() {
    return mLMSJdbcTemplate;
  }

  public void setLoggingEnabled(boolean pLoggingEnabled) {
    mLoggingEnabled = pLoggingEnabled;
  }
}
