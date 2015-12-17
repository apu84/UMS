package org.ums.domain.model;


import java.util.Date;

public interface MutableSemester extends Semester, Mutable {

  void setId(final int pId);

  void setName(final String pName);

  void setStartDate(final Date pDate);

  void setEndDate(final Date pDate);

  void setStatus(final boolean pStatus);

  void setProgramType(final ProgramType pProgram);
}
