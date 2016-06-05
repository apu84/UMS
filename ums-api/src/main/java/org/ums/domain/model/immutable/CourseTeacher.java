package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.mutable.MutableCourseTeacher;

public interface CourseTeacher extends AssignedTeacher, EditType<MutableCourseTeacher> {
  String getTeacherId();

  Teacher getTeacher() throws Exception;

  String getSection();
}
