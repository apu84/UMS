package org.ums.fee.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.ums.fee.FeeType;
import org.ums.fee.dues.MutableStudentDues;
import org.ums.fee.dues.StudentDues;
import org.ums.fee.dues.StudentDuesManager;
import org.ums.fee.semesterfee.InstallmentStatus;
import org.ums.fee.semesterfee.InstallmentStatusManager;
import org.ums.fee.semesterfee.MutableInstallmentStatus;

public class PostStudentPaymentActions extends StudentPaymentDaoDecorator {
  private StudentDuesManager mStudentDuesManager;
  private InstallmentStatusManager mInstallmentStatusManager;

  public PostStudentPaymentActions(StudentDuesManager pStudentDuesManager,
      InstallmentStatusManager pInstallmentStatusManager) {
    Validate.notNull(pStudentDuesManager);
    Validate.notNull(pInstallmentStatusManager);
    mStudentDuesManager = pStudentDuesManager;
    mInstallmentStatusManager = pInstallmentStatusManager;
  }

  @Override
  public int update(List<MutableStudentPayment> pMutableList) {
    if(!pMutableList.stream().allMatch((payment) -> payment.getStatus() == StudentPayment.Status.VERIFIED)) {
      Map<String, List<StudentPayment>> studentPaymentGroup =
          pMutableList.stream().collect(Collectors.groupingBy(payment -> payment.getStudentId()));

      studentPaymentGroup.keySet().forEach((studentId) -> {
        List<StudentDues> duesForStudent = mStudentDuesManager.getByStudent(studentId);

        Map<Integer, List<StudentPayment>> feeTypeGroup = studentPaymentGroup.get(studentId).stream()
            .collect(Collectors.groupingBy(payment -> payment.getFeeCategory().getType().getId()));
        feeTypeGroup.keySet().forEach((feeTypeId) -> {
          if(containsDues(feeTypeId) && !duesForStudent.isEmpty()) {
            postProcessDues(duesForStudent, feeTypeGroup.get(feeTypeId));
          }

          if(containsSemesterFee(feeTypeId)) {
            postProcessSemesterFee(feeTypeGroup.get(feeTypeId));
          }
        });
      });
    }
    return pMutableList.size();
  }

  private void postProcessDues(List<StudentDues> duesForStudent, List<StudentPayment> payments) {
    Map<String, List<MutableStudentDues>> selectedDues = new HashMap<>();
    payments.forEach((payment) -> {
      if(!selectedDues.containsKey(payment.getTransactionId())) {
        List<MutableStudentDues> studentDues =
            duesForStudent.stream().filter((due) -> !StringUtils.isEmpty(due.getTransactionId())
                && due.getTransactionId().equalsIgnoreCase(payment.getTransactionId())).map((due) -> {
                  MutableStudentDues mutableStudentDues = due.edit();
                  if(payment.getStatus() == StudentPayment.Status.EXPIRED) {
                    mutableStudentDues.setTransactionId(StringUtils.EMPTY);
                    mutableStudentDues.setStatus(StudentDues.Status.NOT_PAID);
                  }
                  return mutableStudentDues;
                }).collect(Collectors.toList());
        selectedDues.put(payment.getTransactionId(), studentDues);
      }
    });
    if(!selectedDues.isEmpty()) {
      mStudentDuesManager
          .update(selectedDues.values().stream().flatMap((duesList) -> duesList.stream()).collect(Collectors.toList()));
    }
  }

  private void postProcessSemesterFee(List<? extends StudentPayment> payments) {
    List<InstallmentStatus> installmentStatuses =
        mInstallmentStatusManager.getInstallmentStatus(payments.get(0).getStudentId(), payments.get(0).getSemesterId());
    if(!installmentStatuses.isEmpty()) {
      boolean isExpired = payments.stream().allMatch((payment) -> payment.getStatus() == StudentPayment.Status.EXPIRED);
      installmentStatuses.forEach((status) -> {
        if(!status.isPaymentCompleted() && isExpired) {
          MutableInstallmentStatus mutableStatus = status.edit();
          mutableStatus.delete();
        }
      });
    }
  }

  private boolean containsDues(int pFeeTypeId) {
    return pFeeTypeId == FeeType.Types.DUES.getId() || pFeeTypeId == FeeType.Types.PENALTY.getId();
  }

  private boolean containsSemesterFee(int pFeeTypeId) {
    return pFeeTypeId == FeeType.Types.SEMESTER_FEE.getId();
  }
}
