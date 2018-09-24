package org.ums.domain.model.immutable.optCourse;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupCourseMap;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public interface OptOfferedGroupCourseMap extends Serializable, LastModifier,
    EditType<MutableOptOfferedGroupCourseMap>, Identifier<Long> {
  Long getId();

  Long getGroupId();

  String getCourseId();

  Course getCourses();
}
