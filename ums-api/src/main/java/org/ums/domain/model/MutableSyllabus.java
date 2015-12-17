package org.ums.domain.model;


public interface MutableSyllabus extends Syllabus, Mutable {

  void setId(final String pId);

  void setSemester(final Semester pSemester);

  void setProgram(final Program pProgram);
}
