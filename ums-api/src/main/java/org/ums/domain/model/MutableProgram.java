package org.ums.domain.model;

public interface MutableProgram extends Program, Mutable, MutableLastModifier, MutableIdentifier<Integer> {

  void setShortName(final String pShortName) throws Exception;

  void setLongName(final String pLongName) throws Exception;

  void setDepartment(final Department pDepartment) throws Exception;

  void setDepartmentId(final int pDepartmentId);

  void setProgramType(final ProgramType pProgramType) throws Exception;

  void setProgramTypeId(final int pProgramTypeId);
}
