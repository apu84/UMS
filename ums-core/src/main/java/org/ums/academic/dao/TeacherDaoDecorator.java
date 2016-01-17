package org.ums.academic.dao;


import org.ums.domain.model.mutable.MutableTeacher;
import org.ums.domain.model.regular.Department;
import org.ums.domain.model.regular.Teacher;
import org.ums.manager.TeacherManager;

import java.util.List;

public class TeacherDaoDecorator extends ContentDaoDecorator<Teacher, MutableTeacher, String, TeacherManager> implements TeacherManager {
  @Override
  public List<Teacher> getByDepartment(Department pDepartment) {
    return getManager().getByDepartment(pDepartment);
  }
}
