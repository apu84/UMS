package org.ums.fee.dues;

import java.io.Serializable;
import java.util.Date;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.User;
import org.ums.fee.FeeCategory;

public interface StudentDues extends Serializable, EditType<MutableStudentDues>, LastModifier, Identifier<Long> {

  FeeCategory getFeeCategory();

  String getFeeCategoryId();

  String getDescription();

  String getTransactionId();

  Student getStudent();

  String getStudentId();

  Double getAmount();

  Date getAddedOn();

  Date getPayBefore();

  User getUser();

  String getUserId();
}
