package org.ums.academic.resource.fee.semesterfee;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Parameter;
import org.ums.domain.model.immutable.Student;
import org.ums.fee.*;
import org.ums.fee.latefee.LateFee;
import org.ums.fee.latefee.LateFeeManager;
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
  private SemesterAdmissionStatusManager mSemesterAdmissionStatusManager;
  @Autowired
  private InstallmentSettingsManager mInstallmentSettingsManager;
  @Autowired
  private InstallmentStatusManager mInstallmentStatusManager;
  @Autowired
  private UGFeeManager mUgFeeManager;
  @Autowired
  private LateFeeManager mLateFeeManager;
  @Autowired
  private StudentManager mStudentManager;
  @Autowired
  private FeeCategoryManager mFeeCategoryManager;

  @Override
  public boolean withinFirstInstallmentSlot(Integer pSemesterId) {
    return withInSlot(Parameter.ParameterName.REGULAR_FIRST_INSTALLMENT,
        LateFee.AdmissionType.REGULAR_FIRST_INSTALLMENT, pSemesterId);
  }

  @Override
  public boolean withinSecondInstallmentSlot(Integer pSemesterId) {
    return withInSlot(Parameter.ParameterName.REGULAR_SECOND_INSTALLMENT,
        LateFee.AdmissionType.REGULAR_SECOND_INSTALLMENT, pSemesterId);
  }

  @Override
  public UGFees firstInstallment(String pStudentId, Integer pSemesterId) {
    List<UGFee> ugFees = getFee(pStudentId, pSemesterId).getUGFees();
    ugFees = ugFees.stream()
        .filter((fee) -> !fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.TUITION.toString()))
        .collect(Collectors.toList());
    UGFees fees = new UGFees(ugFees);
    fees.setUGLateFee(getLateFee(pSemesterId, LateFee.AdmissionType.REGULAR_FIRST_INSTALLMENT));
    return fees;
  }

  @Override
  public UGFees secondInstallment(String pStudentId, Integer pSemesterId) {
    Student student = mStudentManager.get(pStudentId);
    List<UGFee> ugFees = mUgFeeManager.getFee(student.getProgram().getFaculty().getId(), student.getSemesterId(),
        mFeeCategoryManager.getFeeCategories(FeeType.Types.SEMESTER_FEE.getId()));
    List<UGFee> installmentFees = ugFees.stream().filter(
        (fee) -> fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.INSTALLMENT_CHARGE.toString()))
        .collect(Collectors.toList());

    List<UGFee> totalRegularAdmissionFees = getFee(pStudentId, pSemesterId).getUGFees();
    List<UGFee> admissionFees = totalRegularAdmissionFees.stream()
        .filter((fee) -> fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.TUITION.toString()))
        .collect(Collectors.toList());
    admissionFees.addAll(installmentFees);
    UGFees fees = new UGFees(admissionFees);
    fees.setUGLateFee(getLateFee(pSemesterId, LateFee.AdmissionType.REGULAR_SECOND_INSTALLMENT));
    return fees;
  }

  @Override
  public UGFees getFee(String pStudentId, Integer pSemesterId) {
    Student student = mStudentManager.get(pStudentId);
    List<UGFee> ugFees = mUgFeeManager.getFee(student.getProgram().getFaculty().getId(), student.getSemesterId());
    ugFees = ugFees.stream()
        .filter((fee) -> fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.ADMISSION.toString())
            || fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.ESTABLISHMENT.toString())
            || fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.TUITION.toString())
            || fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.LABORATORY.toString()))
        .collect(Collectors.toList());
    UGFees fees = new UGFees(ugFees);
    fees.setUGLateFee(getLateFee(pSemesterId, LateFee.AdmissionType.REGULAR_ADMISSION));
    return fees;
  }

  @Override
  public boolean withInAdmissionSlot(Integer pSemesterId) {
    UGSemesterFeeResponse installmentStatus = getInstallmentStatus(pSemesterId);
    return withInSlot(Parameter.ParameterName.REGUALR_ADMISSION, LateFee.AdmissionType.REGULAR_ADMISSION, pSemesterId)
        || (installmentStatus == UGSemesterFeeResponse.INSTALLMENT_AVAILABLE && (withinFirstInstallmentSlot(pSemesterId) || withinSecondInstallmentSlot(pSemesterId)));
  }

  @Override
  public boolean installmentAvailable(String pStudentId, Integer pSemesterId) {
    return getInstallmentStatus(pSemesterId) == UGSemesterFeeResponse.INSTALLMENT_AVAILABLE;
  }

  @Override
  public UGSemesterFeeResponse getAdmissionStatus(String pStudentId, Integer pSemesterId) {
    return UGSemesterFeeResponse.ALLOWED;
  }

  @Override
  public UGSemesterFeeResponse pay(String pStudentId, Integer pSemesterId) {
    // TODO: Validate payment request
    return !withInAdmissionSlot(pSemesterId) ? UGSemesterFeeResponse.NOT_WITHIN_SLOT : payFee(
        getFee(pStudentId, pSemesterId), Parameter.ParameterName.REGUALR_ADMISSION, pStudentId, pSemesterId);
  }

  @Override
  public UGSemesterFeeResponse payFirstInstallment(String pStudentId, Integer pSemesterId) {
    // TODO: Validate payment request
    return !withinFirstInstallmentSlot(pSemesterId) ? UGSemesterFeeResponse.NOT_WITHIN_SLOT : payFee(
        firstInstallment(pStudentId, pSemesterId), Parameter.ParameterName.REGULAR_FIRST_INSTALLMENT, pStudentId,
        pSemesterId);
  }

  @Override
  public UGSemesterFeeResponse paySecondInstallment(String pStudentId, Integer pSemesterId) {
    // TODO: Validate payment request
    return !withinSecondInstallmentSlot(pSemesterId) ? UGSemesterFeeResponse.NOT_WITHIN_SLOT : payFee(
        secondInstallment(pStudentId, pSemesterId), Parameter.ParameterName.REGULAR_SECOND_INSTALLMENT, pStudentId,
        pSemesterId);
  }

  @Override
  ParameterSettingManager getParameterSettingManager() {
    return mParameterSettingManager;
  }

  LateFeeManager getLateFeeManager() {
    return mLateFeeManager;
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

  @Override
  FeeCategoryManager getFeeCategoryManager() {
    return mFeeCategoryManager;
  }
}
