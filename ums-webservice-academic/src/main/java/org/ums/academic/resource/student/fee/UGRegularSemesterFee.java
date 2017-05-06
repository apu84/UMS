package org.ums.academic.resource.student.fee;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Parameter;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeTypeManager;
import org.ums.fee.UGFee;
import org.ums.fee.UGFeeManager;
import org.ums.fee.latefee.UGLateFee;
import org.ums.fee.latefee.UGLateFeeManager;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.fee.semesterfee.InstallmentSettingsManager;
import org.ums.fee.semesterfee.InstallmentStatusManager;
import org.ums.fee.semesterfee.SemesterAdmissionStatusManager;
import org.ums.manager.ParameterSettingManager;
import org.ums.manager.StudentManager;
import org.ums.manager.StudentRecordManager;

@Component
class UGRegularSemesterFee extends AbstractUGSemesterFee {
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
  @Autowired
  private StudentRecordManager mStudentRecordManager;
  @Autowired
  UGFeeManager mUgFeeManager;
  @Autowired
  UGLateFeeManager mUgLateFeeManager;
  @Autowired
  StudentManager mStudentManager;

  @Override
  public boolean withinFirstInstallmentSlot(Integer pSemesterId) {
    return withInSlot(Parameter.ParameterName.REGULAR_FIRST_INSTALLMENT,
        UGLateFee.AdmissionType.REGULAR_FIRST_INSTALLMENT, pSemesterId);
  }

  @Override
  public boolean withinSecondInstallmentSlot(Integer pSemesterId) {
    return withInSlot(Parameter.ParameterName.READMISSION_SECOND_INSTALLMENT,
        UGLateFee.AdmissionType.READMISSION_SECOND_INSTALLMENT, pSemesterId);
  }

  @Override
  public List<UGFee> firstInstallment(String pStudentId, Integer pSemesterId) {
    List<UGFee> ugFees = getFee(pStudentId, pSemesterId);
    return ugFees.stream()
        .filter((fee) -> !fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.TUITION.toString()))
        .collect(Collectors.toList());
  }

  @Override
  public List<UGFee> secondInstallment(String pStudentId, Integer pSemesterId) {
    List<UGFee> ugFees =
        mUgFeeManager.getFee(mStudentManager.get(pStudentId).getProgram().getFaculty().getId(), pSemesterId);
    List<UGFee> installmentFees = ugFees.stream().filter(
        (fee) -> fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.INSTALLMENT_CHARGE.toString()))
        .collect(Collectors.toList());

    List<UGFee> totalRegularAdmissionFees = getFee(pStudentId, pSemesterId);
    List<UGFee> admissionFees = totalRegularAdmissionFees.stream()
        .filter((fee) -> fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.TUITION.toString()))
        .collect(Collectors.toList());
    admissionFees.addAll(installmentFees);
    return admissionFees;
  }

  @Override
  public List<UGFee> getFee(String pStudentId, Integer pSemesterId) {
    List<UGFee> ugFees =
        mUgFeeManager.getFee(mStudentManager.get(pStudentId).getProgram().getFaculty().getId(), pSemesterId);
    return ugFees.stream()
        .filter((fee) -> fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.ADMISSION.toString())
            || fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.ESTABLISHMENT.toString())
            || fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.TUITION.toString())
            || fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.LABORATORY.toString()))
        .collect(Collectors.toList());
  }

  @Override
  public boolean withInAdmissionSlot(Integer pSemesterId) {
    UGSemesterFeeResponse installmentStatus = getInstallmentStatus(pSemesterId);
    return withInSlot(Parameter.ParameterName.REGUALR_ADMISSION, UGLateFee.AdmissionType.REGULAR_ADMISSION, pSemesterId)
        || (installmentStatus == UGSemesterFeeResponse.INSTALLMENT_AVAILABLE && (withinFirstInstallmentSlot(pSemesterId) || withinSecondInstallmentSlot(pSemesterId)));
  }

  @Override
  public boolean installmentAvailable(String pStudentId, Integer pSemesterId) {
    return getInstallmentStatus(pSemesterId) == UGSemesterFeeResponse.INSTALLMENT_AVAILABLE;
  }

  @Override
  public UGSemesterFeeResponse getAdmissionStatus(String pStudentId, Integer pSemesterId) {
    return null;
  }

  @Override
  ParameterSettingManager getParameterSettingManager() {
    return mParameterSettingManager;
  }

  @Override
  UGLateFeeManager getUGLateFeeManager() {
    return mUGLateFeeManager;
  }

  @Override
  StudentRecordManager getStudentRecordManager() {
    return mStudentRecordManager;
  }

  @Override
  InstallmentSettingsManager getInstallmentSettingsManager() {
    return mInstallmentSettingsManager;
  }

  @Override
  StudentPaymentManager getStudentPaymentManager() {
    return mStudentPaymentManager;
  }

  @Override
  FeeTypeManager getFeeTypeManager() {
    return mFeeTypeManager;
  }

  @Override
  SemesterAdmissionStatusManager getSemesterAdmissionStatusManager() {
    return mSemesterAdmissionStatusManager;
  }

  @Override
  InstallmentStatusManager getInstallmentStatusManager() {
    return mInstallmentStatusManager;
  }
}
