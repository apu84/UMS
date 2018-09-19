package org.ums.domain.model.immutable.optCourse;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedSubGroupCourseMap;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public interface OptOfferedSubGroupCourseMap extends Serializable, LastModifier,
    EditType<MutableOptOfferedSubGroupCourseMap>, Identifier<Long> {
  Long getId();

  Long getSubGroupId();

  String getCourseId();

  Course getCourses();
}
