package org.ums.manager.optCourse;

import org.ums.domain.model.immutable.optCourse.OptSeatAllocation;
import org.ums.domain.model.mutable.optCourse.MutableOptSeatAllocation;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public interface OptSeatAllocationManager extends ContentManager<OptSeatAllocation, MutableOptSeatAllocation, Long> {
  List<OptSeatAllocation> getInfoBySemesterId(final Integer pSemesterId, final Integer pProgramId, final Integer pYear,
      final Integer pSemester);
}
