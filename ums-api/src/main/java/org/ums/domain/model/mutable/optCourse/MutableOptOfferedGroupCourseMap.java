package org.ums.domain.model.mutable.optCourse;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupCourseMap;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public interface MutableOptOfferedGroupCourseMap extends OptOfferedGroupCourseMap, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
  void setId(Long pId);

  void setGroupId(Long pGroupId);

  void setCourseId(final String pCourseId);

  void setCourses(final Course pCourses);
}
