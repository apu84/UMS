package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.ums.cache.CacheFactory;
import org.ums.fee.*;
import org.ums.fee.accounts.PaymentAccountMappingDao;
import org.ums.fee.accounts.PaymentAccountsMappingManager;
import org.ums.fee.accounts.PaymentStatusDao;
import org.ums.fee.accounts.PaymentStatusManager;
import org.ums.fee.certificate.*;
import org.ums.fee.dues.StudentDuesDao;
import org.ums.fee.dues.StudentDuesManager;
import org.ums.fee.latefee.LateFeeDao;
import org.ums.fee.latefee.LateFeeManager;
import org.ums.fee.payment.PostPaymentActions;
import org.ums.fee.payment.PostStudentPaymentActions;
import org.ums.fee.payment.StudentPaymentDao;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.fee.semesterfee.InstallmentSettingsDao;
import org.ums.fee.semesterfee.InstallmentSettingsManager;
import org.ums.fee.semesterfee.InstallmentStatusDao;
import org.ums.fee.semesterfee.InstallmentStatusManager;
import org.ums.generator.IdGenerator;
import org.ums.manager.SemesterManager;
import org.ums.message.MessageResource;
import org.ums.services.NotificationGenerator;
import org.ums.statistics.JdbcTemplateFactory;
import org.ums.usermanagement.role.RoleManager;
import org.ums.usermanagement.user.UserManager;

@Configuration
@DependsOn("academicConfig")
public class FeeContext {
  @Autowired
  CacheFactory mCacheFactory;

  @Autowired
  JdbcTemplateFactory mTemplateFactory;

  @Autowired
  IdGenerator mIdGenerator;

  @Autowired
  SemesterManager mSemesterManager;

  @Autowired
  NotificationGenerator mNotificationGenerator;

  @Autowired
  MessageResource mMessageResource;

  @Autowired
  UserManager mUserManager;

  @Autowired
  RoleManager mRoleManager;

  @Autowired
  UMSConfiguration mUMSConfiguration;

  @Bean
  FeeCategoryManager feeCategoryManager() {
    FeeCategoryCache feeCategoryCache = new FeeCategoryCache(mCacheFactory.getCacheManager());
    feeCategoryCache.setManager(new PersistentFeeCategoryDao(mTemplateFactory.getJdbcTemplate()));
    return feeCategoryCache;
  }

  @Bean
  UGFeeManager feeManager() {
    UGFeeCache feeCache = new UGFeeCache(mCacheFactory.getCacheManager());
    feeCache.setManager(new PersistentUGFeeDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator, mSemesterManager));
    return feeCache;
  }

  @Bean
  LateFeeManager lateFeeManager() {
    return new LateFeeDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
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

  @Bean
  CertificateStatusManager certificateStatusManager() {
    CertificateNotification notifier =
        new CertificateNotification(mRoleManager, mUserManager, mNotificationGenerator, mMessageResource);
    CertificateStatusDao dao = new CertificateStatusDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
    dao.setManager(notifier);
    return dao;
  }

  @Bean
  CertificateStatusLogManager certificateStatusLogManager() {
    return new CertificateStatusLogDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  StudentPaymentManager studentPaymentManager() {
    PostStudentPaymentActions postPaymentActions =
        new PostStudentPaymentActions(studentDuesManager(), installmentStatusManager());
    StudentPaymentDao studentPaymentDao = new StudentPaymentDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
    studentPaymentDao.setManager(postPaymentActions);
    return studentPaymentDao;
  }

  @Bean
  PaymentAccountsMappingManager paymentAccountsMappingManager() {
    return new PaymentAccountMappingDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  PaymentStatusManager paymentStatusManager() {
    PostPaymentActions postPaymentActions =
        new PostPaymentActions(certificateStatusManager(), installmentStatusManager(), studentPaymentManager());
    PaymentStatusDao paymentStatusDao = new PaymentStatusDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
    paymentStatusDao.setManager(postPaymentActions);
    return paymentStatusDao;
  }
}
