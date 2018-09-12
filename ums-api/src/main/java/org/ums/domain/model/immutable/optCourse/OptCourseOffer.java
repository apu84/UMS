package org.ums.domain.model.immutable.optCourse;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.optCourse.MutableOptCourseOffer;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public interface OptCourseOffer extends Serializable, LastModifier, EditType<MutableOptCourseOffer>, Identifier<Long> {
  Long getId();

  Integer getSemesterId();

  String getDepartmentId();

  Department getDepartment();

  Integer getProgramId();

  String getProgramName();

  Integer getYear();

  Integer getSemester();

  String getCourseId();

  Course getCourses();

  Integer getCallForApplication();

  Integer getApproved();

  Integer getTotal();
}
