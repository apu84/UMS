package org.ums.decorator;

import org.ums.domain.model.mutable.MutableTeacher;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Teacher;
import org.ums.manager.TeacherManager;

import java.util.List;

public class TeacherDaoDecorator extends ContentDaoDecorator<Teacher, MutableTeacher, String, TeacherManager> implements
    TeacherManager {
  @Override
  public List<Teacher> getByDepartment(Department pDepartment) {
    return getManager().getByDepartment(pDepartment);
  }
}
