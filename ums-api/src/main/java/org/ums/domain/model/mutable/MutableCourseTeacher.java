package org.ums.domain.model.mutable;

import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.immutable.Teacher;

public interface MutableCourseTeacher extends CourseTeacher, MutableAssignedTeacher {
  void setTeacher(final Teacher pTeacher);

  void setTeacherId(final String pTeacherId);

  void setSection(final String pSection);
}
