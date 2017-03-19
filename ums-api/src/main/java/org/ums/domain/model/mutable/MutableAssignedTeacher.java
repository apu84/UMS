package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AssignedTeacher;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;

public interface MutableAssignedTeacher extends AssignedTeacher, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {

  void setSemester(final Semester pSemester);

  void setSemesterId(final Integer pSemesterId);

  void setCourse(final Course pCourse);

  void setCourseId(final String pCourseId);
}
