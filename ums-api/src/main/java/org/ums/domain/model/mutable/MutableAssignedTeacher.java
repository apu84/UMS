package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AssignedTeacher;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Teacher;

public interface MutableAssignedTeacher extends AssignedTeacher, Mutable, MutableLastModifier, MutableIdentifier<Integer> {

  void setSemester(final Semester pSemester);

  void setSemesterId(final Integer pSemesterId);

  void setCourse(final Course pCourse);

  void setCourseId(final String pCourseId);
}