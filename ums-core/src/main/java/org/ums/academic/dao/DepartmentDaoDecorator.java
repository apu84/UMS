package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableDepartment;
import org.ums.domain.model.readOnly.Department;
import org.ums.manager.DepartmentManager;

public class DepartmentDaoDecorator extends ContentDaoDecorator<Department, MutableDepartment, String, DepartmentManager> implements DepartmentManager {
}
