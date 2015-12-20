package org.ums.domain.model;


public interface MutableSyllabus extends Syllabus, Mutable, MutableLastModifier, MutableIdentifier<String> {
  void setSemester(final Semester pSemester);

  void setProgram(final Program pProgram);

  void setSemesterId(final int pSemesterId);

  void setProgramId(final int pProgramId);
}
