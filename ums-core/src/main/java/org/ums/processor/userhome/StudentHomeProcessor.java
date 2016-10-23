package org.ums.processor.userhome;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.Student;
import org.ums.manager.StudentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentHomeProcessor extends AbstractUserHomeProcessor {
  @Autowired
  private StudentManager mStudentManager;

  @Override
  public List<Map<String, String>> process(Subject pCurrentSubject) throws Exception {
    String studentId = pCurrentSubject.getPrincipal().toString();
    Student student = mStudentManager.get(studentId);

    List<Map<String, String>> profileContent = new ArrayList<>();

    Map<String, String> studentName = new HashMap<>();
    studentName.put("key", "Name");
    studentName.put("value", student.getFullName());
    profileContent.add(studentName);

    Map<String, String> department = new HashMap<>();
    department.put("key", "Department");
    department.put("value", student.getDepartment().getLongName());
    profileContent.add(department);

    Map<String, String> yearSemester = new HashMap<>();
    yearSemester.put("key", "Year/ Semester");
    yearSemester
        .put("value", student.getCurrentYear() + "/" + student.getCurrentAcademicSemester());
    profileContent.add(yearSemester);

    Map<String, String> currentEnrolledSemester = new HashMap<>();
    currentEnrolledSemester.put("key", "Admitted semester");
    currentEnrolledSemester.put("value", student.getSemester().getName());
    profileContent.add(currentEnrolledSemester);

    Map<String, String> enrollmentStatus = new HashMap<>();
    enrollmentStatus.put("key", "Current enrollment status");
    enrollmentStatus.put("value", student.getEnrollmentType().toString());
    profileContent.add(enrollmentStatus);

    return profileContent;
  }

  public String getAllowedRole() {
    return mAllowedRole;
  }

  public void setAllowedRole(String pAllowedRole) {
    mAllowedRole = pAllowedRole;
  }
}
