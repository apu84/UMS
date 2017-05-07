package org.ums.academic.resource.student.fee;

import java.util.Optional;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.fee.UGFee;
import org.ums.fee.latefee.UGLateFee;
import org.ums.fee.latefee.UGLateFeeManager;

@Component
class SemesterFeeHelper {
  @Autowired
  UGSemesterFeeFactory mUGSemesterFeeFactory;
  @Autowired
  UGLateFeeManager mUGLateFeeManager;

  @Autowired
  FeeConverter mFeeConverter;

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

  UGFees getFee(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).getFee(pStudentId, pSemesterId);
  }

  Boolean withinFirstInstallmentSlot(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).withinFirstInstallmentSlot(pSemesterId);
  }

  Boolean withinSecondInstallmentSlot(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).withinSecondInstallmentSlot(pSemesterId);
  }

  UGFees firstInstallment(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).firstInstallment(pStudentId, pSemesterId);
  }

  UGFees secondInstallment(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).secondInstallment(pStudentId, pSemesterId);
  }

  UGSemesterFee.UGSemesterFeeResponse getAdmissionStatus(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).getAdmissionStatus(pStudentId, pSemesterId);
  }

  Boolean installmentAvailable(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).installmentAvailable(pStudentId, pSemesterId);
  }

  JsonObject generatePayable(UGFees pUgFees) {
    JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    JsonArrayBuilder jsonArray = Json.createArrayBuilder();
    for(UGFee ugFee : pUgFees.getUGFees()) {
      jsonArray.add(mFeeConverter.convert(ugFee));
    }
    Optional<UGLateFee> lateFee = pUgFees.getUGLateFee();
    if(lateFee.isPresent()) {
      jsonArray.add(mFeeConverter.convert(lateFee.get()));
    }
    jsonObject.add("entries", jsonArray);
    return jsonObject.build();
  }

  UGSemesterFee.UGSemesterFeeResponse pay(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).pay(pStudentId, pSemesterId);
  }

  UGSemesterFee.UGSemesterFeeResponse payFirstInstallment(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).payFirstInstallment(pStudentId, pSemesterId);
  }

  UGSemesterFee.UGSemesterFeeResponse paySecondInstallment(String pStudentId, Integer pSemesterId) {
    return getSemesterFee(pStudentId, pSemesterId).paySecondInstallment(pStudentId, pSemesterId);
  }

  private UGSemesterFee getSemesterFee(String pStudentId, Integer pSemesterId) {
    return mUGSemesterFeeFactory.getSemesterFee(pStudentId, pSemesterId);
  }
}
