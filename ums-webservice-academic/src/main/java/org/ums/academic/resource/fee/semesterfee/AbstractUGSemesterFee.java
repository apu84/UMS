package org.ums.academic.resource.fee.semesterfee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.immutable.Parameter;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.fee.*;
import org.ums.fee.latefee.LateFee;
import org.ums.fee.latefee.LateFeeManager;
import org.ums.fee.payment.MutableStudentPayment;
import org.ums.fee.payment.PersistentStudentPayment;
import org.ums.fee.payment.StudentPayment;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.fee.semesterfee.*;
import org.ums.manager.ParameterSettingManager;
import org.ums.manager.StudentRecordManager;

abstract class AbstractUGSemesterFee implements UGSemesterFee {
  boolean withInSlot(Parameter.ParameterName parameterName, LateFee.AdmissionType pLateFeeFor, Integer pSemesterId) {
    Date today = new Date();
    ParameterSetting semesterAdmissionDate =
        getParameterSettingManager().getBySemesterAndParameterId(parameterName.getId(), pSemesterId);
    Validate.notNull(semesterAdmissionDate, "Dates not found for " + parameterName.toString());
    if(semesterAdmissionDate.getStartDate().before(today) && semesterAdmissionDate.getEndDate().after(today)) {
      return true;
    }
    else {
      Optional<LateFee> ugLateFee = getLateFee(pSemesterId, pLateFeeFor);
      return ugLateFee.isPresent();
    }
  }

  Optional<LateFee> getLateFee(Integer pSemesterId, LateFee.AdmissionType pLateFeeFor) {
    Date today = new Date();
    List<LateFee> lateFees = getLateFeeManager().getLateFees(pSemesterId);
    for(LateFee fee : lateFees) {
      if(fee.getFrom().before(today) && fee.getTo().after(today) && fee.getAdmissionType() == pLateFeeFor) {
        return Optional.of(fee);
      }
    }
    return Optional.empty();
  }

  private boolean hasAppliedForPayment(String pStudentId, Integer pSemesterId) {
    List<StudentPayment> payments =
        getStudentPaymentManager().getPayments(pStudentId, pSemesterId,
            getFeeTypeManager().get(FeeType.Types.SEMESTER_FEE.getId()));
    return payments.stream().anyMatch((payment) -> payment.getStatus() == StudentPayment.Status.APPLIED
        || payment.getStatus() == StudentPayment.Status.RECEIVED);
  }

  public UGSemesterFeeResponse getInstallmentStatus(Integer pSemesterId) {
    return getInstallmentSettingsManager().getInstallmentSettings(pSemesterId).isPresent() ? UGSemesterFeeResponse.INSTALLMENT_AVAILABLE
        : UGSemesterFeeResponse.INSTALLMENT_NOT_AVAILABLE;
  }

  @Override
  public UGSemesterFeeResponse getSemesterFeeStatus(String pStudentId, Integer pSemesterId) {
    if(hasAppliedForPayment(pStudentId, pSemesterId)) {
      return UGSemesterFeeResponse.APPLIED;
    }
    else {
      Optional<SemesterAdmissionStatus> admissionStatus =
          getSemesterAdmissionStatusManager().getAdmissionStatus(pStudentId, pSemesterId);
      if(admissionStatus.isPresent() && admissionStatus.get().isAdmitted()) {
        return UGSemesterFeeResponse.ADMITTED;
      }
      return !withInAdmissionSlot(pSemesterId) ? UGSemesterFeeResponse.NOT_WITHIN_SLOT : UGSemesterFeeResponse.ALLOWED;
    }
  }

  @Override
  public UGSemesterFeeResponse getInstallmentStatus(String pStudentId, Integer pSemesterId) {
    List<InstallmentStatus> installmentStatuses =
        getInstallmentStatusManager().getInstallmentStatus(pStudentId, pSemesterId);
    if(installmentStatuses != null) {
      if(installmentStatuses.size() > 1 && installmentStatuses.get(1).isPaymentCompleted()) {
        return UGSemesterFeeResponse.ADMITTED;
      }
      else if(installmentStatuses.size() > 0 && installmentStatuses.get(0).isPaymentCompleted()) {
        return UGSemesterFeeResponse.FIRST_INSTALLMENT_PAID;
      }
    }
    return UGSemesterFeeResponse.INSTALLMENT_NOT_TAKEN;
  }

  @Transactional
  UGSemesterFeeResponse payFee(UGFees fees, Parameter.ParameterName pType, String pStudentId, Integer pSemesterId) {
    Optional<LateFee> lateFee = fees.getUGLateFee();
    Date transactionValid = lateFee.isPresent() ? lateFee.get().getTo() : getTransactionValidTill(pType, pSemesterId);
    List<MutableStudentPayment> payments = new ArrayList<>();
    for(UGFee fee : fees.getUGFees()) {
      payments.add(createPayment(fee, pStudentId, pSemesterId, transactionValid));
    }
    if(lateFee.isPresent()) {
      payments.add(createPayment(lateFee.get(), pStudentId, pSemesterId, transactionValid));
    }
    getStudentPaymentManager().create(payments);
    return UGSemesterFeeResponse.APPLIED;
  }

  private MutableStudentPayment createPayment(UGFee fee, String pStudentId, Integer pSemesterId, Date pValidTill) {
    MutableStudentPayment payment = new PersistentStudentPayment();
    payment.setFeeCategoryId(fee.getFeeCategoryId());
    payment.setStudentId(pStudentId);
    payment.setSemesterId(pSemesterId);
    payment.setAmount(fee.getAmount());
    payment.setTransactionValidTill(pValidTill);
    return payment;
  }

  private MutableStudentPayment createPayment(LateFee fee, String pStudentId, Integer pSemesterId, Date pValidTill) {
    MutableStudentPayment payment = new PersistentStudentPayment();
    payment.setFeeCategoryId(getFeeCategoryManager().getByFeeId(FeeCategory.Categories.LATE_FEE.toString()).getId());
    payment.setStudentId(pStudentId);
    payment.setSemesterId(pSemesterId);
    payment.setAmount(fee.getFee());
    payment.setTransactionValidTill(pValidTill);
    return payment;
  }

  private Date getTransactionValidTill(Parameter.ParameterName pParameterName, Integer pSemesterId) {
    ParameterSetting parameterSetting =
        getParameterSettingManager().getBySemesterAndParameterId(pParameterName.getId(), pSemesterId);
    Validate.notNull(parameterSetting, "Dates not found for " + pParameterName.toString());
    return parameterSetting.getEndDate();
  }

  abstract ParameterSettingManager getParameterSettingManager();

  abstract LateFeeManager getLateFeeManager();

  abstract InstallmentSettingsManager getInstallmentSettingsManager();

  abstract StudentPaymentManager getStudentPaymentManager();

  abstract FeeTypeManager getFeeTypeManager();

  abstract SemesterAdmissionStatusManager getSemesterAdmissionStatusManager();

  abstract InstallmentStatusManager getInstallmentStatusManager();

  abstract FeeCategoryManager getFeeCategoryManager();
}
