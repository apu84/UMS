package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Equivalent;

public interface MutableEquivalent extends Equivalent, Mutable, MutableLastModifier, MutableIdentifier<Integer> {
  void setOldCourseId(final String pOldCourseId);

  void setOldCourse(final Course pOldCourse);

  void setNewCourseId(final String pNewCourseId);

  void setNewCourse(final Course pNewCourse);
}
