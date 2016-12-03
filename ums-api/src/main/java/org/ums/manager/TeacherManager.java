package org.ums.manager;

import org.ums.domain.model.mutable.MutableTeacher;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Teacher;

import java.util.List;

public interface TeacherManager extends ContentManager<Teacher, MutableTeacher, String> {
  List<Teacher> getByDepartment(final Department pDepartment);
}
