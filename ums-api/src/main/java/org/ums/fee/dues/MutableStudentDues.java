package org.ums.fee.dues;

import java.math.BigDecimal;
import java.util.Date;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Student;
import org.ums.usermanagement.user.User;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.fee.FeeCategory;

public interface MutableStudentDues extends StudentDues, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {

  void setFeeCategory(FeeCategory pFeeCategory);

  void setFeeCategoryId(String pFeeCategoryId);

  void setDescription(String pDescription);

  void setTransactionId(String pTransactionId);

  void setStudent(Student pStudent);

  void setStudentId(String pStudentId);

  void setAmount(BigDecimal pAmount);

  void setAddedOn(Date pAddedOn);

  void setPayBefore(Date pPayBefore);

  void setUser(User pUser);

  void setUserId(String pUserId);
}
