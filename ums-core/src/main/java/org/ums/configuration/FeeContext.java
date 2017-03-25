package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ums.cache.CacheFactory;
import org.ums.fee.*;
import org.ums.fee.latefee.UGLateFeeDao;
import org.ums.fee.latefee.UGLateFeeManager;
import org.ums.generator.IdGenerator;
import org.ums.statistics.JdbcTemplateFactory;

@Configuration
public class FeeContext {
  @Autowired
  CacheFactory mCacheFactory;

  @Autowired
  JdbcTemplateFactory mTemplateFactory;

  @Autowired
  IdGenerator mIdGenerator;

  @Bean
  FeeCategoryManager feeCategoryManager() {
    FeeCategoryCache feeCategoryCache = new FeeCategoryCache(mCacheFactory.getCacheManager());
    feeCategoryCache.setManager(new PersistentFeeCategoryDao(mTemplateFactory.getJdbcTemplate()));
    return feeCategoryCache;
  }

  @Bean
  UGFeeManager feeManager() {
    UGFeeCache feeCache = new UGFeeCache(mCacheFactory.getCacheManager());
    feeCache.setManager(new PersistentUGFeeDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return feeCache;
  }

  @Bean
  UGLateFeeManager ugLateFeeManager() {
    return new UGLateFeeDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }
}
