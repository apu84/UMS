package org.ums.decorator;


import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.mutable.MutableSeatPlanGroup;
import org.ums.manager.SeatPlanGroupManager;

import java.util.List;

/**
 * Created by My Pc on 4/20/2016.
 */
public class SeatPlanGroupDaoDecorator extends ContentDaoDecorator<SeatPlanGroup,MutableSeatPlanGroup,Integer,SeatPlanGroupManager> implements SeatPlanGroupManager {
  @Override
  public List<SeatPlanGroup> getGroupBySemester(int pSemesterId,int pExamType) {
    return getManager().getGroupBySemester(pSemesterId,pExamType);
  }

  @Override
  public List<SeatPlanGroup> getBySemesterGroupAndType(int pSemesterId, int pGroupNo, int pType) {
    return getManager().getBySemesterGroupAndType(pSemesterId,pGroupNo,pType);
  }

  @Override
  public int deleteBySemesterAndExamType(int pSemesterId, int pExamType) {
    return getManager().deleteBySemesterAndExamType(pSemesterId,pExamType);
  }

  @Override
  public List<SeatPlanGroup> getGroupBySemesterTypeFromDb(int pSemesterId, int pExamType) {
    return getManager().getGroupBySemesterTypeFromDb(pSemesterId,pExamType);
  }
}
