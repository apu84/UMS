package org.ums.domain.model.mutable.optCourse;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.optCourse.OptOfferedSubGroupCourseMap;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public interface MutableOptOfferedSubGroupCourseMap extends OptOfferedSubGroupCourseMap, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {

  void setId(Long pId);

  void setSubGroupId(Long pSubGroupId);

  void setCourseId(final String pCourseId);

  void setCourses(final Course pCourses);

}
