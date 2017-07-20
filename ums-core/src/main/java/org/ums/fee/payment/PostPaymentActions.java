package org.ums.fee.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeType;
import org.ums.fee.accounts.MutablePaymentStatus;
import org.ums.fee.accounts.PaymentStatus;
import org.ums.fee.accounts.PaymentStatusDaoDecorator;
import org.ums.fee.certificate.CertificateStatus;
import org.ums.fee.certificate.CertificateStatusManager;
import org.ums.fee.certificate.MutableCertificateStatus;
import org.ums.fee.certificate.PersistentCertificateStatus;
import org.ums.fee.semesterfee.*;

public class PostPaymentActions extends PaymentStatusDaoDecorator {
  private CertificateStatusManager mCertificateStatusManager;
  private InstallmentStatusManager mInstallmentStatusManager;
  private StudentPaymentManager mStudentPaymentManager;

  public PostPaymentActions(CertificateStatusManager pCertificateStatusManager,
      InstallmentStatusManager pInstallmentStatusManager, StudentPaymentManager pStudentPaymentManager) {
    Validate.notNull(pCertificateStatusManager);
    Validate.notNull(pInstallmentStatusManager);
    Validate.notNull(pStudentPaymentManager);
    mCertificateStatusManager = pCertificateStatusManager;
    mInstallmentStatusManager = pInstallmentStatusManager;
    mStudentPaymentManager = pStudentPaymentManager;
  }

  @Override
  @Transactional
  public int update(List<MutablePaymentStatus> pMutableList) {
    pMutableList.forEach((paymentStatus) -> processPayments(paymentStatus));
    return pMutableList.size();
  }

  @Override
  public Long create(MutablePaymentStatus pMutable) {
    processPayments(pMutable);
    return pMutable.getId();
  }

  private void processPayments(PaymentStatus paymentStatus) {
    List<StudentPayment> studentPayments =
        mStudentPaymentManager.getTransactionDetails(paymentStatus.getTransactionId());

    if(containsAdmissionFee(studentPayments)
        && paymentStatus.getStatus() == PaymentStatus.Status.VERIFIED) {
      postProcessAdmissionFee(studentPayments);
    }

    List<MutableCertificateStatus> certificateStatusList = new ArrayList<>();
    List<MutableStudentPayment> payments = new ArrayList<>();
    studentPayments.forEach((payment) -> {
      if(paymentStatus.getStatus() == PaymentStatus.Status.VERIFIED) {
        if(payment.getStatus() == StudentPayment.Status.RECEIVED) {
          MutableStudentPayment mutableStudentPayment = payment.edit();
          mutableStudentPayment.setStatus(StudentPayment.Status.VERIFIED);
          payments.add(mutableStudentPayment);
        }
        if(containsCertificate(payment.getFeeCategory())) {
          certificateStatusList.add(certificateStatus(payment));
        }
      }
      else if (paymentStatus.getStatus() == PaymentStatus.Status.REJECTED) {
        if(payment.getStatus() == StudentPayment.Status.RECEIVED) {
          MutableStudentPayment mutableStudentPayment = payment.edit();
          mutableStudentPayment.setStatus(StudentPayment.Status.REJECTED);
          payments.add(mutableStudentPayment);
        }
      }
    });

    if(!payments.isEmpty()) {
      mStudentPaymentManager.update(payments);
    }

    if(!certificateStatusList.isEmpty()) {
      mCertificateStatusManager.create(certificateStatusList);
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

  private boolean containsAdmissionFee(List<StudentPayment> pPayments) {
    return pPayments.stream()
        .allMatch((payment) -> payment.getFeeCategory().getType().getId() == FeeType.Types.SEMESTER_FEE.getId());
  }

  private void postProcessAdmissionFee(List<StudentPayment> pStudentPayments) {
    StudentPayment payment = pStudentPayments.get(0);
    List<InstallmentStatus> installmentStatuses =
        mInstallmentStatusManager.getInstallmentStatus(payment.getStudentId(), payment.getSemesterId());
    if(!installmentStatuses.isEmpty()) {
      installmentStatuses.forEach((pInstallmentStatus -> {
        if(!pInstallmentStatus.isPaymentCompleted()) {
          MutableInstallmentStatus mutableStatus = pInstallmentStatus.edit();
          mutableStatus.setPaymentCompleted(true);
          mutableStatus.setReceivedOn(new Date());
          mutableStatus.update();

          if(pInstallmentStatus.getInstallmentOrder() == 2) {
            setAdmissionStatus(payment.getStudentId(), payment.getSemesterId());
          }
        }
      }));
    }
    else {
      setAdmissionStatus(payment.getStudentId(), payment.getSemesterId());
    }
  }

  private void setAdmissionStatus(String pStudentId, Integer pSemesterId) {
    MutableSemesterAdmissionStatus mutable = new PersistentSemesterAdmissionStatus();
    mutable.setStudentId(pStudentId);
    mutable.setSemesterId(pSemesterId);
    mutable.setAdmitted(true);
    mutable.create();
  }
}
