package org.ums.academic.resource.student.fee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Parameter;
import org.ums.enums.CourseType;
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
import org.ums.readmission.ReadmissionApplication;
import org.ums.readmission.ReadmissionApplicationManager;

@Component
class UGReadmissionFee extends AbstractUGSemesterFee {
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
  @Autowired
  private ReadmissionApplicationManager mReadmissionApplicationManager;
  @Autowired
  private UGRegularSemesterFee mUgRegularSemesterFee;

  @Override
  public boolean withinFirstInstallmentSlot(Integer pSemesterId) {
    return withInSlot(Parameter.ParameterName.READMISSION_FIRST_INSTALLMENT,
        UGLateFee.AdmissionType.READMISSION_FIRST_INSTALLMENT, pSemesterId);
  }

  @Override
  public boolean withinSecondInstallmentSlot(Integer pSemesterId) {
    return withInSlot(Parameter.ParameterName.READMISSION_SECOND_INSTALLMENT,
        UGLateFee.AdmissionType.READMISSION_SECOND_INSTALLMENT, pSemesterId);
  }

  @Override
  public List<UGFee> firstInstallment(String pStudentId, Integer pSemesterId) {
    List<UGFee> ugFees = getFee(pStudentId, pSemesterId);
    return ugFees.stream().filter(
        (fee) -> !fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.THEORY_REPEATER.toString()))
        .collect(Collectors.toList());
  }

  @Override
  public List<UGFee> secondInstallment(String pStudentId, Integer pSemesterId) {
    List<UGFee> ugFees =
        mUgFeeManager.getFee(mStudentManager.get(pStudentId).getProgram().getFaculty().getId(), pSemesterId);
    List<UGFee> installmentFees = ugFees.stream().filter(
        (fee) -> fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.INSTALLMENT_CHARGE.toString()))
        .collect(Collectors.toList());

    List<UGFee> totalReadmissionFees = getFee(pStudentId, pSemesterId);
    List<UGFee> readmissionFees = totalReadmissionFees.stream().filter(
        (fee) -> fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.THEORY_REPEATER.toString()))
        .collect(Collectors.toList());
    readmissionFees.addAll(installmentFees);
    return readmissionFees;
  }

  @Override
  public List<UGFee> getFee(String pStudentId, Integer pSemesterId) {
    List<UGFee> ugFees =
        mUgFeeManager.getFee(mStudentManager.get(pStudentId).getProgram().getFaculty().getId(), pSemesterId);
    ugFees = ugFees.stream()
        .filter((fee) -> fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.READMISSION.toString())
            || fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.ESTABLISHMENT.toString()))
        .collect(Collectors.toList());

    List<ReadmissionApplication> applications =
        mReadmissionApplicationManager.getReadmissionApplication(pSemesterId, pStudentId);
    List<ReadmissionApplication> theory =
        applications.stream().filter((application) -> application.getCourse().getCourseType().equals(CourseType.THEORY))
            .collect(Collectors.toList());
    List<ReadmissionApplication> sessional = applications.stream()
        .filter((application) -> application.getCourse().getCourseType().equals(CourseType.SESSIONAL))
        .collect(Collectors.toList());

    Optional<UGFee> theoryFee = ugFees.stream().filter(
        (fee) -> fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.THEORY_REPEATER.toString()))
        .findFirst();
    Optional<UGFee> sessionalFee = ugFees.stream().filter(
        (fee) -> fee.getFeeCategory().getFeeId().equalsIgnoreCase(FeeCategory.Categories.SESSIONAL_REPEATER.toString()))
        .findFirst();
    if(theoryFee.isPresent()) {
      theoryFee.get().edit().setAmount(theoryFee.get().getAmount() * theory.size());
      ugFees.add(theoryFee.get());
    }
    if(sessionalFee.isPresent()) {
      sessionalFee.get().edit().setAmount(sessionalFee.get().getAmount() * sessional.size());
      ugFees.add(sessionalFee.get());
    }
    return ugFees;
  }

  @Override
  public boolean withInAdmissionSlot(Integer pSemesterId) {
    UGSemesterFeeResponse installmentStatus = getInstallmentStatus(pSemesterId);
    return withInSlot(Parameter.ParameterName.READMISSION, UGLateFee.AdmissionType.READMISSION, pSemesterId)
        || (installmentStatus == UGSemesterFeeResponse.INSTALLMENT_AVAILABLE && (withinFirstInstallmentSlot(pSemesterId) || withinSecondInstallmentSlot(pSemesterId)));
  }

  @Override
  public boolean installmentAvailable(String pStudentId, Integer pSemesterId) {
    List<UGFee> regularAdmissionFees = mUgRegularSemesterFee.getFee(pStudentId, pSemesterId);
    List<UGFee> readmissionFees = getFee(pStudentId, pSemesterId);

    Double totalRegularAdmissionFee = 0D, totalReadmissionFee = 0D;
    for(UGFee fee : regularAdmissionFees) {
      totalRegularAdmissionFee += fee.getAmount();
    }
    for(UGFee fee : readmissionFees) {
      totalReadmissionFee += fee.getAmount();
    }

    return totalReadmissionFee >= (totalRegularAdmissionFee / 2);
  }

  @Override
  public UGSemesterFeeResponse getAdmissionStatus(String pStudentId, Integer pSemesterId) {
    List<ReadmissionApplication> applications =
        mReadmissionApplicationManager.getReadmissionApplication(pSemesterId, pStudentId);
    return applications != null && applications.size() > 0 ? UGSemesterFeeResponse.READMISSION_APPLIED
        : UGSemesterFeeResponse.READMISSION_NOT_APPLIED;
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
