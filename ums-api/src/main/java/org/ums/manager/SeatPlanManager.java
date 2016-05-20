package org.ums.manager;

import org.ums.domain.model.immutable.SeatPlan;
import org.ums.domain.model.mutable.MutableSeatPlan;

import java.util.List;

/**
 * Created by My Pc on 5/8/2016.
 */
public interface SeatPlanManager extends ContentManager<SeatPlan,MutableSeatPlan,Integer> {
  List<SeatPlan> getBySemesterAndGroupAndExamType(final int pSemesterId, final int pGropNo,final int pExamType);
  List<SeatPlan> getByRoomSemesterGroupExamType(final int pRoomId,final int pSemesterId,final int pGroupNo,final int pExamType);
  int deleteBySemesterGroupExamType(final int pSemesterId,final int pGroupNo,final int pExamType);
  List<SeatPlan> getBySemesterGroupTypeRoomRowAndCol(final int pSemesterId,final int pGroupNo,final int pType,int pRoomId,int pRow,int pCol);
  int checkIfExistsBySemesterGroupTypeRoomRowAndCol(final int pSemesterId,final int pGroupNo,final int pType,int pRoomId,int pRow,int pCol);
  int checkIfExistsByRoomSemesterGroupExamType(final int pRoomId,final int pSemesterId,final int pGroupNo,final int pExamType);
}
