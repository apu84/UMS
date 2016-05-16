package org.ums.decorator;

import org.ums.domain.model.immutable.SeatPlan;
import org.ums.domain.model.mutable.MutableSeatPlan;
import org.ums.manager.SeatPlanManager;

import java.util.List;

/**
 * Created by My Pc on 5/8/2016.
 */
public class SeatPlanDaoDecorator extends ContentDaoDecorator<SeatPlan,MutableSeatPlan,Integer,SeatPlanManager> implements SeatPlanManager {
  @Override
  public List<SeatPlan> getBySemesterAndGroupAndExamType(int pSemesterId, int pGropNo,int pExamType) {
    return getManager().getBySemesterAndGroupAndExamType(pSemesterId,pGropNo,pExamType);
  }

  @Override
  public List<SeatPlan> getByRoomSemesterGroupExamType(int pRoomId, int pSemesterId, int pGroupNo,int pExamType) {
    return getManager().getByRoomSemesterGroupExamType(pRoomId,pSemesterId,pGroupNo,pExamType);
  }

  @Override
  public int deleteBySemesterGroupExamType(int pSemesterId, int pGroupNo, int pExamType) {
    return getManager().deleteBySemesterGroupExamType(pSemesterId,pGroupNo,pExamType);
  }

  @Override
  public SeatPlan getBySemesterGroupTypeRoomRowAndCol(int pSemesterId, int pGroupNo, int pType, int pRoomId, int pRow, int pCol) {
    return getManager().getBySemesterGroupTypeRoomRowAndCol(pSemesterId,pGroupNo,pType,pRoomId,pRow,pCol);
  }
}
