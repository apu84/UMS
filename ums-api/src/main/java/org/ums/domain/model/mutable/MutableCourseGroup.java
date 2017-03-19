package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.CourseGroup;
import org.ums.domain.model.immutable.Syllabus;

public interface MutableCourseGroup extends CourseGroup, Editable<Integer>, MutableLastModifier,
    MutableIdentifier<Integer> {
  void setName(final String pName);

  void setSyllabus(final Syllabus pSyllabus);

  void setSyllabusId(final String pSyllabusId);
}
