package org.ums.academic.resource.student.fee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.fee.UGFee;

@Component
class SemesterFeeHelper {
  @Autowired
  UGSemesterFeeFactory mUGSemesterFeeFactory;

  UGSemesterFee.UGSemesterFeeResponse getSemesterFeeStatus(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).getSemesterFeeStatus(pStudentId, pSemesterId);
  }

  UGSemesterFee.UGSemesterFeeResponse getInstallmentStatus(Integer pSemesterId, String pStudentId) {
    return getSemesterFee(pStudentId, pSemesterId).getInstallmentStatus(pSemesterId);
  }

  UGSemesterFee.UGSemesterFeeResponse getInstallmentStatus(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).getInstallmentStatus(pStudentId, pSemesterId);
  }

  Boolean withInAdmissionSlot(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).withInAdmissionSlot(pSemesterId);
  }

  List<UGFee> getFee(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).getFee(pStudentId, pSemesterId);
  }

  Boolean withinFirstInstallmentSlot(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).withinFirstInstallmentSlot(pSemesterId);
  }

  Boolean withinSecondInstallmentSlot(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).withinSecondInstallmentSlot(pSemesterId);
  }

  List<UGFee> firstInstallment(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).firstInstallment(pStudentId, pSemesterId);
  }

  List<UGFee> secondInstallment(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).secondInstallment(pStudentId, pSemesterId);
  }

  UGSemesterFee.UGSemesterFeeResponse getAdmissionStatus(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).getAdmissionStatus(pStudentId, pSemesterId);
  }

  Boolean installmentAvailable(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).installmentAvailable(pStudentId, pSemesterId);
  }

  private UGSemesterFee getSemesterFee(String pStudentId, Integer pSemesterId) {
    return mUGSemesterFeeFactory.getSemesterFee(pStudentId, pSemesterId);
  }
}
