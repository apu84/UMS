package org.ums.fee.dues;

import org.ums.manager.ContentManager;

import java.util.List;

public interface StudentDuesManager extends ContentManager<StudentDues, MutableStudentDues, Long> {
  List<StudentDues> getByStudent(String pStudentId);
}
