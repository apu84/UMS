package org.ums.domain.model.immutable.optCourse;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.optCourse.MutableOptSeatAllocation;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public interface OptSeatAllocation extends Serializable, LastModifier, EditType<MutableOptSeatAllocation>,
    Identifier<Long> {
  Long getId();

  Long getGroupID();

  String getDepartmentId();

  Integer getSeatNumber();
}
