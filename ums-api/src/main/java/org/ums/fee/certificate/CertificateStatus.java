package org.ums.fee.certificate;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.User;
import org.ums.fee.FeeCategory;

import java.io.Serializable;
import java.util.Date;

public interface CertificateStatus
    extends
    Serializable,
    EditType<MutableCertificateStatus>,
    LastModifier,
    Identifier<Long> {

  FeeCategory getFeeCategory();

  String getFeeCategoryId();

  String getTransactionId();

  Student getStudent();

  String getStudentId();

  Semester getSemester();

  Integer getSemesterId();

  Date getProcessedOn();

  boolean isStatus();

  User getUser();

  String getUserId();
}