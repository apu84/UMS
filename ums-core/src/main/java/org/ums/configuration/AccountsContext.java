package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.ums.cache.CacheFactory;
import org.ums.generator.IdGenerator;
import org.ums.statistics.JdbcTemplateFactory;

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

}
