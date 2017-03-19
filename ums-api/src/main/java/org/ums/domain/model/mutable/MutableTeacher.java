package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Teacher;

public interface MutableTeacher extends Teacher, Editable<String>, MutableLastModifier,
    MutableIdentifier<String> {
  void setName(final String pName);

  void setDesignationId(final String pDesignationId);

  void setDesignationName(final String pDesignationName);

  void setDepartmentId(final String pDepartmentId);

  void setDepartmentName(final String pDepartmentName);

  void setDepartment(final Department pDepartment);

  void setStatus(final boolean pStatus);
}
