package org.ums.manager;

import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.immutable.Student;

import java.util.List;


public interface StudentManager extends ContentManager<Student, MutableStudent, String> {
  public List<Student> getStudentListFromStudentsString(final String pStudents) throws Exception;
}
