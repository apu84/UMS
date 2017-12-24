package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ums.cache.CacheFactory;
import org.ums.generator.IdGenerator;
import org.ums.manager.accounts.GroupManager;
import org.ums.persistent.dao.accounts.PersistentGroupDao;
import org.ums.statistics.JdbcTemplateFactory;
import org.ums.statistics.NamedParameterJdbcTemplateFactory;

/**
 * Created by Monjur-E-Morshed on 05-Dec-17.
 */
@Configuration
public class AccountsContext {

  @Autowired
  CacheFactory mCacheFactory;
  @Autowired
  JdbcTemplateFactory mTemplateFactory;
  @Autowired
  IdGenerator mIdGenerator;
  @Autowired
  NamedParameterJdbcTemplateFactory mNamedParameterJdbcTemplateFactory;
  @Autowired
  UMSConfiguration mUMSConfiguration;

  @Bean
  GroupManager groupManager() {
    return new PersistentGroupDao(mTemplateFactory.getAccountsJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getAccountNamedParameterJdbcTemplate(), mIdGenerator);
  }

}
