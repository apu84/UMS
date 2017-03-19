package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.EquivalentCourse;

public interface MutableEquivalentCourse extends EquivalentCourse, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
  void setOldCourseId(final String pOldCourseId);

  void setOldCourse(final Course pOldCourse);

  void setNewCourseId(final String pNewCourseId);

  void setNewCourse(final Course pNewCourse);
}
