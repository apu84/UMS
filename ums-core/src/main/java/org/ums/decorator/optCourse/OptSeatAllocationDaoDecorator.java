package org.ums.decorator.optCourse;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptSeatAllocation;
import org.ums.domain.model.mutable.optCourse.MutableOptSeatAllocation;
import org.ums.manager.optCourse.OptSeatAllocationManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public class OptSeatAllocationDaoDecorator extends
    ContentDaoDecorator<OptSeatAllocation, MutableOptSeatAllocation, Long, OptSeatAllocationManager> implements
    OptSeatAllocationManager {
  @Override
  public List<OptSeatAllocation> getInfoBySemesterId(Integer pSemesterId, Integer pProgramId, Integer pYear,
      Integer pSemester) {
    return getManager().getInfoBySemesterId(pSemesterId, pProgramId, pYear, pSemester);
  }
}
