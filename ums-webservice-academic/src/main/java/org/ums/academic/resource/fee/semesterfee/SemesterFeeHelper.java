package org.ums.academic.resource.fee.semesterfee;

import java.util.Optional;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.fee.UGFee;
import org.ums.fee.latefee.LateFee;
import org.ums.fee.latefee.LateFeeManager;

@Component
class SemesterFeeHelper {
  @Autowired
  UGSemesterFeeFactory mUGSemesterFeeFactory;
  @Autowired
  LateFeeManager mLateFeeManager;

  @Autowired
  FeeConverter mFeeConverter;

  UGSemesterFee.UGSemesterFeeResponse getSemesterFeeStatus(String pStudentId, Integer pSemesterId) {
    return getSemesterFeeType(pStudentId, pSemesterId).getSemesterFeeStatus(pStudentId, pSemesterId);
  }

  UGSemesterFee.UGSemesterFeeResponse getInstallmentStatus(Integer pSemesterId, String pStudentId) {
    return getSemesterFeeType(pStudentId, pSemesterId).getInstallmentStatus(pSemesterId);
  }

  UGSemesterFee.UGSemesterFeeResponse getInstallmentStatus(String pStudentId, Integer pSemesterId) {
    return getSemesterFeeType(pStudentId, pSemesterId).getInstallmentStatus(pStudentId, pSemesterId);
  }

  Boolean withInAdmissionSlot(String pStudentId, Integer pSemesterId) {
    return getSemesterFeeType(pStudentId, pSemesterId).withInAdmissionSlot(pSemesterId);
  }

  UGFees getFee(String pStudentId, Integer pSemesterId) {
    return getSemesterFeeType(pStudentId, pSemesterId).getFee(pStudentId, pSemesterId);
  }

  Boolean withinFirstInstallmentSlot(String pStudentId, Integer pSemesterId) {
    return getSemesterFeeType(pStudentId, pSemesterId).withinFirstInstallmentSlot(pSemesterId);
  }

  Boolean withinSecondInstallmentSlot(String pStudentId, Integer pSemesterId) {
    return getSemesterFeeType(pStudentId, pSemesterId).withinSecondInstallmentSlot(pSemesterId);
  }

  UGFees firstInstallment(String pStudentId, Integer pSemesterId) {
    return getSemesterFeeType(pStudentId, pSemesterId).firstInstallment(pStudentId, pSemesterId);
  }

  UGFees secondInstallment(String pStudentId, Integer pSemesterId) {
    return getSemesterFeeType(pStudentId, pSemesterId).secondInstallment(pStudentId, pSemesterId);
  }

  UGSemesterFee.UGSemesterFeeResponse getAdmissionStatus(String pStudentId, Integer pSemesterId) {
    return getSemesterFeeType(pStudentId, pSemesterId).getAdmissionStatus(pStudentId, pSemesterId);
  }

  Boolean installmentAvailable(String pStudentId, Integer pSemesterId) {
    return getSemesterFeeType(pStudentId, pSemesterId).installmentAvailable(pStudentId, pSemesterId);
  }

  JsonObject generatePayable(UGFees pUgFees) {
    JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    JsonArrayBuilder jsonArray = Json.createArrayBuilder();
    for(UGFee ugFee : pUgFees.getUGFees()) {
      jsonArray.add(mFeeConverter.convert(ugFee));
    }
    Optional<LateFee> lateFee = pUgFees.getUGLateFee();
    if(lateFee.isPresent()) {
      jsonArray.add(mFeeConverter.convert(lateFee.get()));
    }
    jsonObject.add("entries", jsonArray);
    return jsonObject.build();
  }

  UGSemesterFee.UGSemesterFeeResponse pay(String pStudentId, Integer pSemesterId) {
    return getSemesterFeeType(pStudentId, pSemesterId).pay(pStudentId, pSemesterId);
  }

  UGSemesterFee.UGSemesterFeeResponse payFirstInstallment(String pStudentId, Integer pSemesterId) {
    return getSemesterFeeType(pStudentId, pSemesterId).payFirstInstallment(pStudentId, pSemesterId);
  }

  UGSemesterFee.UGSemesterFeeResponse paySecondInstallment(String pStudentId, Integer pSemesterId) {
    return getSemesterFeeType(pStudentId, pSemesterId).paySecondInstallment(pStudentId, pSemesterId);
  }

  private UGSemesterFee getSemesterFeeType(String pStudentId, Integer pSemesterId) {
    return mUGSemesterFeeFactory.getSemesterFeeType(pStudentId, pSemesterId);
  }
}
