package org.ums.domain.model.mutable.optCourse;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.optCourse.OptSeatAllocation;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public interface MutableOptSeatAllocation extends OptSeatAllocation, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
  void setId(Long pId);

  void setGroupID(Long pGroupId);

  void setDepartmentId(String pDepartmentId);

  void setSeatNumber(Integer pSeatNumber);
}
