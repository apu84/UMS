package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.DeptDesignationMap;
import org.ums.domain.model.immutable.Designation;

public interface MutableDeptDesignationMap extends DeptDesignationMap, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {

  void setDepartment(final Department pDepartment);

  void setDepartmentId(final String pDepartmentId);

  void setEmployeeTypeId(final int pEmployeeTypeId);

  void setDesignationId(final int pDesignationId);

  void setDesignation(final Designation pDesignation);
}
