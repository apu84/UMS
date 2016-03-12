package org.ums.domain.model.mutable;

import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.ProgramType;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;

public interface MutableProgram extends Program, Mutable, MutableLastModifier, MutableIdentifier<Integer> {

  void setShortName(final String pShortName) throws Exception;

  void setLongName(final String pLongName) throws Exception;

  void setDepartment(final Department pDepartment) throws Exception;

  void setDepartmentId(final String pDepartmentId);

  void setProgramType(final ProgramType pProgramType) throws Exception;

  void setProgramTypeId(final int pProgramTypeId);
}
