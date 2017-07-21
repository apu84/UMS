package org.ums.fee.payment;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.ums.configuration.UMSConfiguration;

public class PaymentValidatorJob implements PaymentValidator {
  private static final Logger mLogger = LoggerFactory.getLogger(PaymentValidatorJob.class);
  private StudentPaymentManager mStudentPaymentManager;
  private SecurityManager mSecurityManager;
  private UMSConfiguration mUMSConfiguration;

  public PaymentValidatorJob(StudentPaymentManager pStudentPaymentManager, SecurityManager pSecurityManager,
      UMSConfiguration pUMSConfiguration) {
    mStudentPaymentManager = pStudentPaymentManager;
    mSecurityManager = pSecurityManager;
    mUMSConfiguration = pUMSConfiguration;
  }

  @Scheduled(fixedDelay = 120000, initialDelay = 240000)
  @Transactional
  public void validatePayments() {
    if(login()) {
      List<StudentPayment> payments = mStudentPaymentManager.getToExpirePayments();
      if(mLogger.isDebugEnabled()) {
        mLogger.debug(String.format("Found total %d payments to expire", payments.size()));
      }
      List<MutableStudentPayment> mutablePayments =
          payments.stream().map(payment -> payment.edit()).collect(Collectors.toList());
      mutablePayments.forEach(payment -> payment.setStatus(StudentPayment.Status.EXPIRED));
      if(!mutablePayments.isEmpty()) {
        mStudentPaymentManager.update(mutablePayments);
      }
    }
  }

  private boolean login() {
    SecurityUtils.setSecurityManager(mSecurityManager);
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token =
        new UsernamePasswordToken(mUMSConfiguration.getBackendUser(), mUMSConfiguration.getBackendUserPassword());

    try {
      // Authenticate the subject
      subject.login(token);
      return true;
    } catch(Exception e) {
      mLogger.error("Exception while login using back end user ", e);
    }
    return false;
  }
}
