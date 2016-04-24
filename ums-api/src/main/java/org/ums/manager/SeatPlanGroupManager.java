package org.ums.manager;

import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.mutable.MutableSeatPlanGroup;

import java.util.List;

/**
 * Created by My Pc on 4/20/2016.
 */
public interface SeatPlanGroupManager extends ContentManager<SeatPlanGroup,MutableSeatPlanGroup,Integer> {
  List<SeatPlanGroup> getGroupBySemester(final int pSemesterId,final int pExamType);
}
