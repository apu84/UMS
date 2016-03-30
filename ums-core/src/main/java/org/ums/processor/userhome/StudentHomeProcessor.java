package org.ums.processor.userhome;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.Role;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.User;
import org.ums.manager.StudentManager;
import org.ums.manager.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentHomeProcessor implements UserHomeProcessor {
  private static String ALLOWED_ROLES_SEPARATOR = ",";
  private String mAllowedRole;

  @Autowired
  private UserManager mUserManager;
  @Autowired
  private StudentManager mStudentManager;

  @Override
  public List<Map<String, String>> process(Subject pCurrentSubject) throws Exception {
    String studentId = pCurrentSubject.getPrincipal().toString();
    Student student = mStudentManager.get(studentId);

    List<Map<String, String>> profileContent = new ArrayList<>();

    Map<String, String> studentName = new HashMap<>();
    studentName.put("Name", student.getFullName());
    profileContent.add(studentName);

    Map<String, String> department = new HashMap<>();
    studentName.put("Department", student.getDepartment().getLongName());
    profileContent.add(department);

    Map<String, String> yearSemester = new HashMap<>();
    studentName.put("Year/ Semester", student.getCurrentYear() + "/" + student.getCurrentAcademicSemester());
    profileContent.add(yearSemester);

    Map<String, String> currentEnrolledSemester = new HashMap<>();
    studentName.put("Current semester", student.getSemester().getName());
    profileContent.add(currentEnrolledSemester);

    Map<String, String> enrollmentStatus = new HashMap<>();
    studentName.put("Current enrollment status", student.getEnrollmentType().toString());
    profileContent.add(enrollmentStatus);

    return profileContent;
  }

  @Override
  public boolean supports(Subject pCurrentSubject) throws Exception {
    String userId = pCurrentSubject.getPrincipal().toString();
    User user = mUserManager.get(userId);

    if (user != null) {
      Role userRole = user.getPrimaryRole();
      if (mAllowedRole.contains(ALLOWED_ROLES_SEPARATOR)) {
        String[] allowedRoles = mAllowedRole.split(ALLOWED_ROLES_SEPARATOR);

        for (String allowedRole : allowedRoles) {
          if (allowedRole.equalsIgnoreCase(userRole.getName())) {
            return true;
          }
        }
      } else {
        return userRole.getName().equalsIgnoreCase(mAllowedRole);
      }
    }

    return false;
  }

  public String getAllowedRole() {
    return mAllowedRole;
  }

  public void setAllowedRole(String pAllowedRole) {
    mAllowedRole = pAllowedRole;
  }
}
