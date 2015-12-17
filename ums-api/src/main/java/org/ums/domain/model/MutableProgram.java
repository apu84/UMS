package org.ums.domain.model;

public interface MutableProgram extends Program, Mutable {
  void setId(final int pId) throws Exception;

  void setShortName(final String pShortName) throws Exception;

  void setLongName(final String pLongName) throws Exception;

  void setDepartment(final Department pDepartment) throws Exception;

  void setProgramType(final ProgramType pProgramType) throws Exception;
}
