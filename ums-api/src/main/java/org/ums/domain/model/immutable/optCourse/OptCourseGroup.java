package org.ums.domain.model.immutable.optCourse;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.optCourse.MutableOptCourseGroup;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public interface OptCourseGroup extends Serializable, LastModifier, EditType<MutableOptCourseGroup>, Identifier<Long> {
  Long getId();

  Integer getOptGroupId();

  String getOptGroupName();

}
