package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.readOnly.Semester;
import org.ums.domain.model.readOnly.Student;
import org.ums.domain.model.readOnly.StudentRecord;

public interface MutableStudentRecord extends StudentRecord, Mutable, MutableIdentifier<Integer>, MutableLastModifier {
  void setStudentId(final String pStudentId);

  void setStudent(final Student pStudent);

  void setSemesterId(final Integer pSemesterId);

  void setSemester(final Semester pSemester);

  void setYear(final Integer pYear);

  void setAcademicSemester(final Integer pAcademicSemester);

  void setType(final Type pType);

  void setStatus(final Status pStatus);

  void setCGPA(final Float pCGPA);

  void setGPA(final Float pGPA);
}
