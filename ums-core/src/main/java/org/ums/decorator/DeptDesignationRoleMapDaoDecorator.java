package org.ums.decorator;

import org.ums.domain.model.immutable.DeptDesignationRoleMap;
import org.ums.domain.model.mutable.MutableDeptDesignationRoleMap;
import org.ums.manager.DeptDesignationRoleMapManager;

import java.util.List;

public class DeptDesignationRoleMapDaoDecorator extends
    ContentDaoDecorator<DeptDesignationRoleMap, MutableDeptDesignationRoleMap, Integer, DeptDesignationRoleMapManager>
    implements DeptDesignationRoleMapManager {
  @Override
  public List<DeptDesignationRoleMap> getDeptDesignationMap(String departmentId, int pEmployeeId) {
    return getManager().getDeptDesignationMap(departmentId, pEmployeeId);
  }

  @Override
  public List<DeptDesignationRoleMap> getDeptDesignationMap(String departmentId) {
    return getManager().getDeptDesignationMap(departmentId);
  }
}
