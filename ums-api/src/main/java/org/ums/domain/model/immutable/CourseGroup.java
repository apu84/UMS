package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableCourseGroup;

import java.io.Serializable;

// Course Group for Optional Courses
public interface CourseGroup extends Serializable, EditType<MutableCourseGroup>, LastModifier, Identifier<Integer> {
  String getName();

  Syllabus getSyllabus();

  String getSyllabusId();
}
