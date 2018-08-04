package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.DeptDesignationRoleMap;
import org.ums.domain.model.immutable.Designation;
import org.ums.usermanagement.role.Role;

public interface MutableDeptDesignationRoleMap extends DeptDesignationRoleMap, Editable<Integer>,
    MutableIdentifier<Integer>, MutableLastModifier {

  void setDepartment(final Department pDepartment);

  void setDepartmentId(final String pDepartmentId);

  void setEmployeeTypeId(final int pEmployeeTypeId);

  void setDesignationId(final int pDesignationId);

  void setDesignation(final Designation pDesignation);

  void setRole(final Role pRole);

  void setRoleId(final int pRoleId);
}
