package org.ums.domain.model.mutable;


import org.ums.domain.model.readOnly.Program;
import org.ums.domain.model.readOnly.Semester;
import org.ums.domain.model.readOnly.Syllabus;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;

public interface MutableSyllabus extends Syllabus, Mutable, MutableLastModifier, MutableIdentifier<String> {
  void setSemester(final Semester pSemester);

  void setProgram(final Program pProgram);

  void setSemesterId(final int pSemesterId);

  void setProgramId(final int pProgramId);
}
