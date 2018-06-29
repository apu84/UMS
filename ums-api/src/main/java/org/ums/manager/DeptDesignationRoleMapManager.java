package org.ums.manager;

import org.ums.domain.model.immutable.DeptDesignationRoleMap;
import org.ums.domain.model.mutable.MutableDeptDesignationRoleMap;

import java.util.List;

public interface DeptDesignationRoleMapManager extends
    ContentManager<DeptDesignationRoleMap, MutableDeptDesignationRoleMap, Integer> {

  List<DeptDesignationRoleMap> getDeptDesignationMap(final String departmentId, final int pEmployeeTypeId);

  List<DeptDesignationRoleMap> getDeptDesignationMap(final String departmentId);
}
