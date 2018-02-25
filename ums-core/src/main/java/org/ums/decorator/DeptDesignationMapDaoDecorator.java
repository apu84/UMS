package org.ums.decorator;

import org.ums.domain.model.immutable.DeptDesignationMap;
import org.ums.domain.model.mutable.MutableDeptDesignationMap;
import org.ums.manager.DeptDesignationMapManager;

public class DeptDesignationMapDaoDecorator extends
    ContentDaoDecorator<DeptDesignationMap, MutableDeptDesignationMap, Integer, DeptDesignationMapManager> implements
    DeptDesignationMapManager {
}
