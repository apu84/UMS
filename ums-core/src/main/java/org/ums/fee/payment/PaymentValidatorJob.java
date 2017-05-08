package org.ums.fee.payment;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentValidatorJob {
  private static final Logger mLogger = LoggerFactory.getLogger(PaymentValidatorJob.class);

  @Autowired
  StudentPaymentManager mStudentPaymentManager;

  @Scheduled(fixedDelay = 300000, initialDelay = 60000)
  @Transactional
  void validatePayments() {
    List<StudentPayment> payments = mStudentPaymentManager.getToExpirePayments();
    if(mLogger.isDebugEnabled()) {
      mLogger.debug(String.format("Found total %d payments to expire", payments.size()));
    }
    List<MutableStudentPayment> mutablePayments =
        payments.stream().map(payment -> payment.edit()).collect(Collectors.toList());
    mutablePayments.forEach(payment -> payment.setStatus(StudentPayment.Status.EXPIRED));
    mStudentPaymentManager.update(mutablePayments);
  }
}
