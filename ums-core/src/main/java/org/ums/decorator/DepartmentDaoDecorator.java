package org.ums.decorator;

import org.ums.cache.ContentDaoDecorator;
import org.ums.domain.model.mutable.MutableDepartment;
import org.ums.domain.model.immutable.Department;
import org.ums.manager.DepartmentManager;

public class DepartmentDaoDecorator extends ContentDaoDecorator<Department, MutableDepartment, String, DepartmentManager> implements DepartmentManager {
}
