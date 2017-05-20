package org.ums.fee.certificate;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.fee.certificate.CertificateStatus;

import java.util.Date;

import org.ums.fee.FeeCategory;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.User;

public interface MutableCertificateStatus extends CertificateStatus, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setFeeCategory(FeeCategory pFeeCategory);

  void setFeeCategoryId(String pFeeCategoryId);

  void setTransactionId(String pTransactionId);

  void setStudent(Student pStudent);

  void setStudentId(String pStudentId);

  void setSemester(Semester pSemester);

  void setSemesterId(Integer pSemesterId);

  void setProcessedOn(Date pProcessedOn);

  void setStatus(Status pStatus);

  void setUser(User pUser);

  void setUserId(String pUserId);
}
