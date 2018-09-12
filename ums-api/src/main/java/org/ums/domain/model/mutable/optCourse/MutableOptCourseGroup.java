package org.ums.domain.model.mutable.optCourse;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.optCourse.OptCourseGroup;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public interface MutableOptCourseGroup extends OptCourseGroup, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
  void setId(Long pId);

  void setOptGroupId(final Integer pGroupId);

  void setOptGroupName(final String pGroupName);
}
