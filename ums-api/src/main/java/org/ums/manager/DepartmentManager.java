package org.ums.manager;

import org.ums.domain.model.mutable.MutableDepartment;
import org.ums.domain.model.immutable.Department;

public interface DepartmentManager extends ContentManager<Department, MutableDepartment, String> {
}
