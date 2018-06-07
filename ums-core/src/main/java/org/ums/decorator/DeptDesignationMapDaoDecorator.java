package org.ums.decorator;

import org.ums.domain.model.immutable.DeptDesignationMap;
import org.ums.domain.model.mutable.MutableDeptDesignationMap;
import org.ums.manager.DeptDesignationMapManager;

import java.util.List;

public class DeptDesignationMapDaoDecorator extends
    ContentDaoDecorator<DeptDesignationMap, MutableDeptDesignationMap, Integer, DeptDesignationMapManager> implements
    DeptDesignationMapManager {
  @Override
  public List<DeptDesignationMap> getDeptDesignationMap(String departmentId, int pEmployeeId) {
    return getManager().getDeptDesignationMap(departmentId, pEmployeeId);
  }

  @Override
  public List<DeptDesignationMap> getDeptDesignationMap(String departmentId) {
    return getManager().getDeptDesignationMap(departmentId);
  }
}
