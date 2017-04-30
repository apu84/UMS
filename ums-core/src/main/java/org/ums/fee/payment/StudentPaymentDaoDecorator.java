package org.ums.fee.payment;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.fee.FeeType;

public class StudentPaymentDaoDecorator extends
    ContentDaoDecorator<StudentPayment, MutableStudentPayment, Long, StudentPaymentManager> implements
    StudentPaymentManager {
  @Override
  public List<StudentPayment> getPayments(String pStudentId, Integer pSemesterId) {
    return getManager().getPayments(pStudentId, pSemesterId);
  }

  @Override
  public List<StudentPayment> getPayments(String pStudentId, Integer pSemesterId, FeeType pFeeType) {
    return getManager().getPayments(pStudentId, pSemesterId, pFeeType);
  }
}
