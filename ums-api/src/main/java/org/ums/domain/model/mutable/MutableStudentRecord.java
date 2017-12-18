package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.StudentRecord;

public interface MutableStudentRecord extends StudentRecord, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {
  void setStudentId(final String pStudentId);

  void setStudent(final Student pStudent);

  void setSemesterId(final Integer pSemesterId);

  void setSemester(final Semester pSemester);

  void setYear(final Integer pYear);

  void setAcademicSemester(final Integer pAcademicSemester);

  void setType(final Type pType);

  void setStatus(final Status pStatus);

  void setCGPA(final Double pCGPA);

  void setGPA(final Double pGPA);

  void setProgramId(final Integer pProgramId);

  void setProgram(final Program pProgram);

  void setGradesheetRemarks(final String pRemarks);

  void setTabulationSheetRemarks(final String pRemarks);
}
