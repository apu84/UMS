package org.ums.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Monjur-E-Morshed on 24-Dec-17.
 */
@Component
public class NamedParameterJdbcTemplateFactoryImpl implements NamedParameterJdbcTemplateFactory {

  private boolean mLoggingEnabled;

  @Autowired
  @Qualifier("namedParameterJdbcTemplate")
  NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

  @Autowired
  @Qualifier("lmsNamedParameterJdbcTemplate")
  NamedParameterJdbcTemplate mLMSNamedParameterJdbcTemplate;

  @Autowired
  @Qualifier("accountsNamedParameterJdbcTemplate")
  NamedParameterJdbcTemplate mAccountsNamedParameterJdbcTemplated;

  @Override
  public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
    return mNamedParameterJdbcTemplate;
  }

  @Override
  public NamedParameterJdbcTemplate getLmsNamedParameterJdbcTemplate() {
    return mLMSNamedParameterJdbcTemplate;
  }

  @Override
  public NamedParameterJdbcTemplate getAccountNamedParameterJdbcTemplate() {
    return mAccountsNamedParameterJdbcTemplated;
  }

  public void setLoggingEnabled(boolean pLoggingEnabled) {
    mLoggingEnabled = pLoggingEnabled;
  }
}
