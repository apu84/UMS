package org.ums.fee.dues;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;

public class StudentDuesDaoDecorator extends
    ContentDaoDecorator<StudentDues, MutableStudentDues, Long, StudentDuesManager> implements StudentDuesManager {
  @Override
  public List<StudentDues> getByStudent(String pStudentId) {
    return getManager().getByStudent(pStudentId);
  }
}
