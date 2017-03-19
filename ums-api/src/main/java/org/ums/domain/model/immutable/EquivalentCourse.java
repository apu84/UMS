package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableEquivalentCourse;

import java.io.Serializable;

public interface EquivalentCourse extends Serializable, Identifier<Long>, EditType<MutableEquivalentCourse>,
    LastModifier {
  String getOldCourseId();

  Course getOldCourse();

  String getNewCourseId();

  Course getNewCourse();
}
