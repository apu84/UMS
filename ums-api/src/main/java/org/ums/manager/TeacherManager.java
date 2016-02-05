package org.ums.manager;


import org.ums.domain.model.mutable.MutableTeacher;
import org.ums.domain.model.readOnly.Department;
import org.ums.domain.model.readOnly.Teacher;

import java.util.List;

public interface TeacherManager extends ContentManager<Teacher, MutableTeacher, String> {
  List<Teacher> getByDepartment(final Department pDepartment);
}
