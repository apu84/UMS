package org.ums.fee.payment;

import org.ums.fee.FeeType;
import org.ums.manager.ContentManager;

import java.util.List;

public interface StudentPaymentManager extends ContentManager<StudentPayment, MutableStudentPayment, Long> {
  List<StudentPayment> getPayments(String pStudentId, Integer pSemesterId);

  List<StudentPayment> getPayments(String pStudentId, Integer pSemesterId, FeeType pFeeType);
}
