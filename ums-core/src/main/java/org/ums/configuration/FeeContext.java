package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ums.cache.CacheFactory;
import org.ums.fee.*;
import org.ums.fee.dues.StudentDuesDao;
import org.ums.fee.dues.StudentDuesManager;
import org.ums.fee.latefee.UGLateFeeDao;
import org.ums.fee.latefee.UGLateFeeManager;
import org.ums.fee.payment.StudentPaymentDao;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.fee.semesterfee.InstallmentSettingsDao;
import org.ums.fee.semesterfee.InstallmentSettingsManager;
import org.ums.fee.semesterfee.InstallmentStatusDao;
import org.ums.fee.semesterfee.InstallmentStatusManager;
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

  @Bean
  StudentPaymentManager studentPaymentManager() {
    return new StudentPaymentDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  FeeTypeManager feeTypeManager() {
    return new FeeTypeDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  InstallmentSettingsManager installmentSettingsManager() {
    return new InstallmentSettingsDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  InstallmentStatusManager installmentStatusManager() {
    return new InstallmentStatusDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  StudentDuesManager studentDuesManager() {
    return new StudentDuesDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }
}
