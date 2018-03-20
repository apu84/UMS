package org.ums.result.legacy;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableLegacyTabulation extends LegacyTabulation, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setGpa(Double pGpa);

  void setCgpa(Double pCgpa);

  void setComment(String pComment);

  void setYear(Integer pYear);

  void setAcademicSemester(Integer pAcademicSemester);

  void setSemester(Semester pSemester);

  void setSemesterId(Integer pSemesterId);

  void setStudent(Student pStudent);

  void setStudentId(String pStudentId);
}
