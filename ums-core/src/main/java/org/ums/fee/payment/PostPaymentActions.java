package org.ums.fee.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.shiro.SecurityUtils;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeType;
import org.ums.fee.certificate.CertificateStatus;
import org.ums.fee.certificate.CertificateStatusManager;
import org.ums.fee.certificate.MutableCertificateStatus;
import org.ums.fee.certificate.PersistentCertificateStatus;
import org.ums.fee.dues.MutableStudentDues;
import org.ums.fee.dues.StudentDues;
import org.ums.fee.dues.StudentDuesManager;

public class PostPaymentActions extends StudentPaymentDaoDecorator {
  private CertificateStatusManager mCertificateStatusManager;
  private StudentDuesManager mStudentDuesManager;

  public PostPaymentActions(CertificateStatusManager pCertificateStatusManager, StudentDuesManager pStudentDuesManager) {
    Validate.notNull(pCertificateStatusManager);
    Validate.notNull(pStudentDuesManager);
    mCertificateStatusManager = pCertificateStatusManager;
    mStudentDuesManager = pStudentDuesManager;
  }

  @Override
  public int update(List<MutableStudentPayment> pMutableList) {
    List<MutableCertificateStatus> certificateStatusList = new ArrayList<>();
    List<StudentDues> duesForStudent = mStudentDuesManager.getByStudent(pMutableList.get(0).getStudentId());
    pMutableList.forEach((payment) -> {
      if(containsCertificate(payment.getFeeCategory()) && payment.getStatus() == StudentPayment.Status.RECEIVED) {
        certificateStatusList.add(certificateStatus(payment));
      }
      if(containsDues(payment.getFeeCategory()) && duesForStudent.size() > 0) {
        postProcessDues(duesForStudent, payment);
      }
    });
    if(certificateStatusList.size() > 0) {
      mCertificateStatusManager.create(certificateStatusList);
    }
    return pMutableList.size();
  }

  private void postProcessDues(List<StudentDues> duesForStudent, StudentPayment payment) {
    List<MutableStudentDues> studentDues = duesForStudent.stream()
        .filter((due) -> due.getTransactionId().equalsIgnoreCase(payment.getTransactionId())).map((due) -> {
          MutableStudentDues mutableStudentDues = due.edit();
          if(payment.getStatus() == StudentPayment.Status.RECEIVED) {
            mutableStudentDues.setStatus(StudentDues.Status.PAID);
          }
          else if(payment.getStatus() == StudentPayment.Status.EXPIRED) {
            mutableStudentDues.setTransactionId(StringUtils.EMPTY);
            mutableStudentDues.setStatus(StudentDues.Status.NOT_PAID);
          }
          return mutableStudentDues;
        }).collect(Collectors.toList());
    if(studentDues.size() > 0) {
      mStudentDuesManager.update(studentDues);
    }
  }

  private MutableCertificateStatus certificateStatus(StudentPayment payment) {
    MutableCertificateStatus mutable = new PersistentCertificateStatus();
    mutable.setFeeCategoryId(payment.getFeeCategoryId());
    mutable.setStudentId(payment.getStudentId());
    mutable.setSemesterId(payment.getSemesterId());
    mutable.setTransactionId(payment.getTransactionId());
    mutable.setUserId(SecurityUtils.getSubject().getPrincipal().toString());
    mutable.setStatus(CertificateStatus.Status.APPLIED);
    return mutable;
  }

  private boolean containsCertificate(FeeCategory pCategory) {
    return pCategory.getType().getId() == FeeType.Types.CERTIFICATE_FEE.getId();
  }

  private boolean containsDues(FeeCategory pFeeCategory) {
    return pFeeCategory.getType().getId() == FeeType.Types.DUES.getId()
        || pFeeCategory.getType().getId() == FeeType.Types.PENALTY.getId();
  }
}
