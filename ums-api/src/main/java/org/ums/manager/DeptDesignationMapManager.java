package org.ums.manager;

import org.ums.domain.model.immutable.DeptDesignationMap;
import org.ums.domain.model.mutable.MutableDeptDesignationMap;

import java.util.List;

public interface DeptDesignationMapManager extends
    ContentManager<DeptDesignationMap, MutableDeptDesignationMap, Integer> {

  List<DeptDesignationMap> getDeptDesignationMap(final String departmentId, final int pEmployeeTypeId);

  List<DeptDesignationMap> getDeptDesignationMap(final String departmentId);
}
