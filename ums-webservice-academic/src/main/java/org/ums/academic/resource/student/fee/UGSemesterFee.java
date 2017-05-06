package org.ums.academic.resource.student.fee;

import java.util.List;

import org.ums.fee.UGFee;

interface UGSemesterFee {
  boolean withinFirstInstallmentSlot(Integer pSemesterId);

  boolean withinSecondInstallmentSlot(Integer pSemesterId);

  List<UGFee> firstInstallment(String pStudentId, Integer pSemesterId);

  List<UGFee> secondInstallment(String pStudentId, Integer pSemesterId);

  List<UGFee> getFee(String pStudentId, Integer pSemesterId);

  boolean withInAdmissionSlot(Integer pSemesterId);

  UGSemesterFeeResponse getSemesterFeeStatus(String pStudentId, Integer pSemesterId);

  UGSemesterFeeResponse getInstallmentStatus(Integer pSemesterId);

  UGSemesterFeeResponse getInstallmentStatus(String pStudentId, Integer pSemesterId);

  boolean installmentAvailable(String pStudentId, Integer pSemesterId);

  UGSemesterFeeResponse getAdmissionStatus(String pStudentId, Integer pSemesterId);

  enum UGSemesterFeeResponse {
    ADMITTED,
    APPLIED,
    ALLOWED,
    NOT_ALLOWED,
    NOT_WITHIN_SLOT,
    INSTALLMENT_AVAILABLE,
    INSTALLMENT_NOT_AVAILABLE,
    FIRST_INSTALLMENT_PAID,
    INSTALLMENT_NOT_TAKEN,
    READMISSION_APPLIED,
    READMISSION_NOT_APPLIED
  }
}
