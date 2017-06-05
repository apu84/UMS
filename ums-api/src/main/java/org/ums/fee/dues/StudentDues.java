package org.ums.fee.dues;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Student;
import org.ums.usermanagement.user.User;
import org.ums.fee.FeeCategory;

public interface StudentDues extends Serializable, EditType<MutableStudentDues>, LastModifier, Identifier<Long> {

  FeeCategory getFeeCategory();

  String getFeeCategoryId();

  String getDescription();

  String getTransactionId();

  Student getStudent();

  String getStudentId();

  BigDecimal getAmount();

  Date getAddedOn();

  Date getPayBefore();

  User getUser();

  String getUserId();
}
