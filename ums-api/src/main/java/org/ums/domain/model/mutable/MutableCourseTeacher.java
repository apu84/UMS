package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.regular.Course;
import org.ums.domain.model.regular.CourseTeacher;
import org.ums.domain.model.regular.Semester;
import org.ums.domain.model.regular.Teacher;

public interface MutableCourseTeacher extends CourseTeacher, Mutable, MutableLastModifier, MutableIdentifier<String> {
  void setSemester(final Semester pSemester);

  void setCourse(final Course pCourse);

  void setTeacher(final Teacher pTeacher);

  void setSection(final String pSection);
}
