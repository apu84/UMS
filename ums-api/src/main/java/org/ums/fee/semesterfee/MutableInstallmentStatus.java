package org.ums.fee.semesterfee;

import java.util.Date;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableInstallmentStatus extends InstallmentStatus, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setReceivedOn(Date pReceivedOn);

  void setPaymentCompleted(Boolean pPaymentCompleted);

  void setInstallmentOrder(Integer pInstallmentOrder);

  void setSemester(Semester pSemester);

  void setSemesterId(Integer pSemesterId);

  void setStudent(Student pStudent);

  void setStudentId(String pStudentId);
}
