package org.ums.decorator;

import org.ums.domain.model.immutable.DesignationRoleMap;
import org.ums.domain.model.mutable.MutableDesignationRoleMap;
import org.ums.manager.DesignationRoleMapManager;

public class DesignationRoleMapDaoDecorator extends
    ContentDaoDecorator<DesignationRoleMap, MutableDesignationRoleMap, Integer, DesignationRoleMapManager> implements
    DesignationRoleMapManager {
}
