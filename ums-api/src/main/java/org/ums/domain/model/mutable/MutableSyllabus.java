package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Syllabus;

public interface MutableSyllabus extends Syllabus, Editable<String>, MutableLastModifier, MutableIdentifier<String> {
  void setSemester(final Semester pSemester);

  void setProgram(final Program pProgram);

  void setSemesterId(final int pSemesterId);

  void setProgramId(final int pProgramId);
}
