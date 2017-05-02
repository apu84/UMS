package org.ums.academic.resource.student.fee;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Parameter;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.fee.FeeType;
import org.ums.fee.FeeTypeManager;
import org.ums.fee.latefee.UGLateFee;
import org.ums.fee.latefee.UGLateFeeManager;
import org.ums.fee.payment.StudentPayment;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.fee.semesterfee.*;
import org.ums.manager.ParameterSettingManager;

@Component
public class SemesterFeeHelper {
  @Autowired
  StudentPaymentManager mStudentPaymentManager;
  @Autowired
  FeeTypeManager mFeeTypeManager;
  @Autowired
  private ParameterSettingManager mParameterSettingManager;
  @Autowired
  private UGLateFeeManager mUGLateFeeManager;
  @Autowired
  private SemesterAdmissionStatusManager mSemesterAdmissionStatusManager;
  @Autowired
  private InstallmentSettingsManager mInstallmentSettingsManager;
  @Autowired
  private InstallmentStatusManager mInstallmentStatusManager;

  boolean withInAdmissionSlot(Integer pSemesterId) {
    Date today = new Date();
    ParameterSetting semesterAdmissionDate =
        mParameterSettingManager.getByParameterAndSemesterId(Parameter.ParameterName.SEMESTER_ADMISSION.getLabel(),
            pSemesterId);
    if(semesterAdmissionDate.getStartDate().after(today) && semesterAdmissionDate.getEndDate().before(today)) {
      return true;
    }
    else {
      Optional<UGLateFee> ugLateFee = getLateFee(pSemesterId);
      return ugLateFee.isPresent();
    }
  }

  private Optional<UGLateFee> getLateFee(Integer pSemesterId) {
    Date today = new Date();
    List<UGLateFee> lateFees = mUGLateFeeManager.getLateFees(pSemesterId);
    for(UGLateFee fee : lateFees) {
      if(fee.getFrom().after(today) && fee.getTo().before(today)) {
        return Optional.of(fee);
      }
    }
    return Optional.empty();
  }

  SemesterFeeResponseType getSemesterFeeStatus(String pStudentId, Integer pSemesterId) {
    List<StudentPayment> payments =
        mStudentPaymentManager.getPayments(pStudentId, pSemesterId,
            mFeeTypeManager.get(FeeType.Types.SEMESTER_FEE.getId()));
    if(payments.size() > 0) {
      StudentPayment payment = payments.get(0);
      if(payment.getStatus() == StudentPayment.Status.APPLIED) {
        return SemesterFeeResponseType.APPLIED;
      }
    }
    else {
      if(!withInAdmissionSlot(pSemesterId)) {
        return SemesterFeeResponseType.NOT_WITHIN_SLOT;
      }
      else {
        SemesterAdmissionStatus admissionStatus =
            mSemesterAdmissionStatusManager.getAdmissionStatus(pStudentId, pSemesterId);
        return admissionStatus.isAdmitted() ? SemesterFeeResponseType.ADMITTED : SemesterFeeResponseType.ALLOWED;
      }
    }
    return SemesterFeeResponseType.NOT_ALLOWED;
  }

  SemesterFeeResponseType getInstallmentStatus(Integer pSemesterId) {
    return mInstallmentSettingsManager.getInstallmentSettings(pSemesterId).isPresent() ? SemesterFeeResponseType.INSTALLMENT_AVAILABLE
        : SemesterFeeResponseType.INSTALLMENT_NOT_AVAILABLE;
  }

  SemesterFeeResponseType getInstallmentStatus(String pStudentId, Integer pSemesterId) {
    List<InstallmentStatus> installmentStatuses =
        mInstallmentStatusManager.getInstallmentStatus(pStudentId, pSemesterId);
    if(installmentStatuses != null) {
      if(installmentStatuses.size() > 1 && installmentStatuses.get(1).isPaymentCompleted()) {
        return SemesterFeeResponseType.ADMITTED;
      }
      else if(installmentStatuses.size() > 0 && installmentStatuses.get(0).isPaymentCompleted()) {
        return SemesterFeeResponseType.FIRST_INSTALLMENT_PAID;
      }
    }
    return SemesterFeeResponseType.INSTALLMENT_NOT_TAKEN;
  }

  JsonObject generatePayable(String pStudentId, Integer pSemesterId) {
    return null;
  }

  enum SemesterFeeResponseType {
    ADMITTED,
    APPLIED,
    ALLOWED,
    NOT_ALLOWED,
    NOT_WITHIN_SLOT,
    INSTALLMENT_AVAILABLE,
    INSTALLMENT_NOT_AVAILABLE,
    FIRST_INSTALLMENT_PAID,
    INSTALLMENT_NOT_TAKEN
  }
}
