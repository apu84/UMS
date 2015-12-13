package org.ums.domain.model;


public interface MutableSyllabus extends Syllabus {

  void setId(final String pId);

  void setName(final String pName);

  void setSemester(final Semester pSemester);

  void setStatus(final boolean pStatus);

  void commit() throws Exception;

  void delete() throws Exception;
}
