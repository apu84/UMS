package org.ums.fee.semesterfee;

import java.util.Date;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableSemesterAdmissionStatus extends SemesterAdmissionStatus, Editable<Long>,
    MutableIdentifier<Long>, MutableLastModifier {
  void setAdmitted(Boolean pPaymentCompleted);

  void setSemester(Semester pSemester);

  void setSemesterId(Integer pSemesterId);

  void setStudent(Student pStudent);

  void setStudentId(String pStudentId);
}
