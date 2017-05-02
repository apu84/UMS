package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ums.cache.*;
import org.ums.generator.IdGenerator;
import org.ums.manager.*;
import org.ums.persistent.dao.*;
import org.ums.statistics.JdbcTemplateFactory;

@Configuration
public class AdmissionContext {
  @Autowired
  CacheFactory mCacheFactory;

  @Autowired
  JdbcTemplateFactory mTemplateFactory;

  @Autowired
  IdGenerator mIdGenerator;

  @Bean
  AdmissionTotalSeatManager admissionTotalSeatManager() {
    AdmissionTotalSeatCache admissionTotalSeatCache = new AdmissionTotalSeatCache(mCacheFactory.getCacheManager());
    admissionTotalSeatCache.setManager(new PersistentAdmissionTotalSeatDao(mTemplateFactory.getJdbcTemplate()));
    return admissionTotalSeatCache;
  }

  @Bean
  PaymentInfoManager paymentInfoManager() {
    PaymentInfoCache paymentInfoCache = new PaymentInfoCache(mCacheFactory.getCacheManager());
    paymentInfoCache.setManager(new PersistentPaymentInfoDao(mTemplateFactory.getJdbcTemplate()));
    return paymentInfoCache;
  }

  @Bean
  AdmissionStudentManager admissionStudentManager() {
    AdmissionStudentCache admissionStudentCache = new AdmissionStudentCache(mCacheFactory.getCacheManager());
    admissionStudentCache.setManager(new PersistentAdmissionStudentDao(mTemplateFactory.getJdbcTemplate()));
    return admissionStudentCache;
  }

  @Bean
  AdmissionMeritListManager admissionMeritListManager() {
    AdmissionMeritListCache admissionMeritListCache = new AdmissionMeritListCache(mCacheFactory.getCacheManager());
    admissionMeritListCache.setManager(new PersistentAdmissionMeritListDao(mTemplateFactory.getJdbcTemplate()));
    return admissionMeritListCache;
  }

  @Bean
  AdmissionAllTypesOfCertificateManager admissionStudentCertificateManager() {
    AdmissionAllTypesOfCertificateCache admissionStudentCertificateCache =
        new AdmissionAllTypesOfCertificateCache(mCacheFactory.getCacheManager());
    admissionStudentCertificateCache.setManager(new PersistentAdmissionAllTypesOfCertificateDao(mTemplateFactory
        .getJdbcTemplate()));
    return admissionStudentCertificateCache;
  }

  @Bean
  AdmissionCertificatesOfStudentManager admissionStudentsCertificateHistoryManager() {
    AdmissionCertificatesOfStudentCache admissionStudentsCertificateHistoryCache =
        new AdmissionCertificatesOfStudentCache(mCacheFactory.getCacheManager());
    admissionStudentsCertificateHistoryCache.setManager(new PersistentAdmissionCertificatesOfStudentDao(
        mTemplateFactory.getJdbcTemplate()));
    return admissionStudentsCertificateHistoryCache;
  }

  @Bean
  AdmissionCommentForStudentManager admissionStudentsCertificateCommentManager() {
    AdmissionCommentForStudentCache admissionStudentsCertificateCommentCache =
        new AdmissionCommentForStudentCache(mCacheFactory.getCacheManager());
    admissionStudentsCertificateCommentCache.setManager(new PersistentAdmissionCommentForStudentDao(mTemplateFactory
        .getJdbcTemplate()));
    return admissionStudentsCertificateCommentCache;
  }

  @Bean
  DepartmentSelectionDeadlineManager departmentSelectionDeadlineManager() {
    DepartmentSelectionDeadlineCache departmentSelectionDeadlineCache =
        new DepartmentSelectionDeadlineCache(mCacheFactory.getCacheManager());
    departmentSelectionDeadlineCache.setManager(new PersistentDepartmentSelectionDeadlineDao(mTemplateFactory
        .getJdbcTemplate()));
    return departmentSelectionDeadlineCache;
  }
}
