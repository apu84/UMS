package org.ums.fee.payment;

import java.util.List;

import org.ums.fee.FeeType;
import org.ums.manager.ContentManager;

public interface StudentPaymentManager extends ContentManager<StudentPayment, MutableStudentPayment, Long> {
  List<StudentPayment> getPayments(String pStudentId, Integer pSemesterId);

  List<StudentPayment> getPayments(String pStudentId, Integer pSemesterId, FeeType pFeeType);

  List<StudentPayment> getPayments(String pStudentId, FeeType pFeeType);

  List<StudentPayment> getToExpirePayments();

  List<StudentPayment> getTransactionDetails(String pStudentId, String pTransactionId);
}
