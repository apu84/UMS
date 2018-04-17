package org.ums.usermanagement.transformer;

import org.ums.domain.model.immutable.Student;
import org.ums.manager.StudentManager;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;

public class StudentUserResolver implements UserPropertyResolver {
  private StudentManager mStudentManager;

  public StudentUserResolver(StudentManager pStudentManager) {
    mStudentManager = pStudentManager;
  }

  @Override
  public boolean supports(Role pRole) {
    return pRole.getName().equalsIgnoreCase("student");
  }

  @Override
  public User resolve(User pUser) {
    MutableUser mutableUser = pUser.edit();
    Student student = mStudentManager.get(pUser.getId());
    mutableUser.setDepartment(student.getDepartment());
    mutableUser.setName(student.getFullName());
    mutableUser.setEmail(student.getEmail());
    return mutableUser;
  }
}
