package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableDeptDesignationMap;

import java.io.Serializable;

public interface DeptDesignationMap extends Serializable, EditType<MutableDeptDesignationMap>, LastModifier,
    Identifier<Integer> {

  Department getDepartment();

  String getDepartmentId();

  int getEmployeeTypeId();

  int getDesignationId();

  Designation getDesignation();

}
