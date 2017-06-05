package org.ums.fee.payment;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.shiro.SecurityUtils;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeType;
import org.ums.fee.certificate.CertificateStatus;
import org.ums.fee.certificate.CertificateStatusManager;
import org.ums.fee.certificate.MutableCertificateStatus;
import org.ums.fee.certificate.PersistentCertificateStatus;

public class PostPaymentActions extends StudentPaymentDaoDecorator {
  private CertificateStatusManager mCertificateStatusManager;

  public PostPaymentActions(CertificateStatusManager pCertificateStatusManager) {
    Validate.notNull(pCertificateStatusManager);
    mCertificateStatusManager = pCertificateStatusManager;
  }

  @Override
  public int update(List<MutableStudentPayment> pMutableList) {
    List<MutableCertificateStatus> certificateStatusList = new ArrayList<>();
    pMutableList.forEach((payment) -> {
      if(containsCertificate(payment.getFeeCategory()) && payment.getStatus() == StudentPayment.Status.RECEIVED) {
        MutableCertificateStatus mutable = new PersistentCertificateStatus();
        mutable.setFeeCategoryId(payment.getFeeCategoryId());
        mutable.setStudentId(payment.getStudentId());
        mutable.setSemesterId(payment.getSemesterId());
        mutable.setTransactionId(payment.getTransactionId());
        mutable.setUserId(SecurityUtils.getSubject().getPrincipal().toString());
        mutable.setStatus(CertificateStatus.Status.APPLIED);
        certificateStatusList.add(mutable);
      }
    });
    mCertificateStatusManager.create(certificateStatusList);
    return pMutableList.size();
  }

  private boolean containsCertificate(FeeCategory pCategory) {
    return pCategory.getType().getId() == FeeType.Types.CERTIFICATE_FEE.getId();
  }
}
