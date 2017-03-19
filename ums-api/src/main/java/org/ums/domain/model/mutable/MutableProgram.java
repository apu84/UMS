package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.ProgramType;

public interface MutableProgram extends Program, Editable<Integer>, MutableLastModifier, MutableIdentifier<Integer> {

  void setShortName(final String pShortName);

  void setLongName(final String pLongName);

  void setDepartment(final Department pDepartment);

  void setDepartmentId(final String pDepartmentId);

  void setProgramType(final ProgramType pProgramType);

  void setProgramTypeId(final int pProgramTypeId);

  void setFaculty(final Faculty pFaculty);

  void setFacultyId(final int pFacultyId);
}
