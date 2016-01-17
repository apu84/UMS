package org.ums.domain.model.regular;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableCourseTeacher;

import java.io.Serializable;

public interface CourseTeacher extends Serializable, LastModifier, EditType<MutableCourseTeacher>, Identifier<String> {
  Semester getSemester() throws Exception;

  Course getCourse() throws Exception;

  Teacher getTeacher() throws Exception;

  String getSection();
}
