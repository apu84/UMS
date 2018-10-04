package org.ums.domain.model.immutable.optCourse;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.optCourse.MutableOptStudentCourseSelection;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public interface OptStudentCourseSelection extends Serializable, LastModifier,
    EditType<MutableOptStudentCourseSelection>, Identifier<Long> {
  Long getId();

  Long getGroupId();

  Long getSubGroupId();

  String getStudentId();

  Integer getStudentChoice();

  Integer getProgramId();

  Integer getSemesterId();

  Integer getYear();

  Integer getSemester();

  Integer getDepartmentId();

}
