package org.ums.domain.model.mutable;


import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.readOnly.Program;
import org.ums.domain.model.readOnly.Semester;
import org.ums.domain.model.readOnly.SemesterEnrollment;

import java.util.Date;

public interface MutableSemesterEnrollment extends SemesterEnrollment, Mutable, MutableIdentifier<Integer>, MutableLastModifier {
  void setSemesterId(final Integer pSemesterId);

  void setSemester(final Semester pSemester);

  void setProgramId(final Integer pProgramId);

  void setProgram(final Program pProgram);

  void setYear(final Integer pYear);

  void setAcademicSemester(final Integer pAcademicSemester);

  void setEnrollmentDate(final Date pEnrollmentDate);

  void setType(final Type pType);
}
