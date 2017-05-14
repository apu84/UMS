package org.ums.academic.resource.fee.semesterfee;

interface UGSemesterFee {
  boolean withinFirstInstallmentSlot(Integer pSemesterId);

  boolean withinSecondInstallmentSlot(Integer pSemesterId);

  UGFees firstInstallment(String pStudentId, Integer pSemesterId);

  UGFees secondInstallment(String pStudentId, Integer pSemesterId);

  UGFees getFee(String pStudentId, Integer pSemesterId);

  boolean withInAdmissionSlot(Integer pSemesterId);

  UGSemesterFeeResponse getSemesterFeeStatus(String pStudentId, Integer pSemesterId);

  UGSemesterFeeResponse getInstallmentStatus(Integer pSemesterId);

  UGSemesterFeeResponse getInstallmentStatus(String pStudentId, Integer pSemesterId);

  boolean installmentAvailable(String pStudentId, Integer pSemesterId);

  UGSemesterFeeResponse getAdmissionStatus(String pStudentId, Integer pSemesterId);

  UGSemesterFeeResponse pay(String pStudentId, Integer pSemesterId);

  UGSemesterFeeResponse payFirstInstallment(String pStudentId, Integer pSemesterId);

  UGSemesterFeeResponse paySecondInstallment(String pStudentId, Integer pSemesterId);

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
