package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableDeptDesignationRoleMap;
import org.ums.usermanagement.role.Role;

import java.io.Serializable;

public interface DeptDesignationRoleMap extends Serializable, EditType<MutableDeptDesignationRoleMap>, LastModifier,
    Identifier<Integer> {

  Department getDepartment();

  String getDepartmentId();

  int getEmployeeTypeId();

  int getDesignationId();

  Designation getDesignation();

  Role getRole();

  int getRoleId();

}
