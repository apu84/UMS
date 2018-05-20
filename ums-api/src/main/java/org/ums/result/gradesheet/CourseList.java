package org.ums.result.gradesheet;

import java.util.List;

public interface CourseList<T> {
  List<T> getTheoryCourses();

  List<T> getSessionalCourses();
}
