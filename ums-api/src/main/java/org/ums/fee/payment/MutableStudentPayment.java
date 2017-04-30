package org.ums.fee.payment;

import java.util.Date;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.fee.FeeType;

public interface MutableStudentPayment extends StudentPayment, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setTransactionId(String pTransactionId);

  void setSemester(Semester pSemester);

  void setSemesterId(Integer pSemesterId);

  void setStudent(Student pStudent);

  void setStudentId(String pStudentId);

  void setAmount(Double pAmount);

  void setStatus(Status pStatus);

  void setAppliedOn(Date pAppliedOn);

  void setVerifiedOn(Date pVerifiedOn);

  void setFeeTypeId(Integer feeTypeId);

  void setFeeType(FeeType feeType);
}
