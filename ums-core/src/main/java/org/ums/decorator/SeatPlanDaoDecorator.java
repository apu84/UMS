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
  public int createSeatPlanForCCI(List<MutableSeatPlan> pSeatPlans) throws Exception{
    return getManager().createSeatPlanForCCI(pSeatPlans);
  }

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
  public int deleteBySemesterGroupExamTypeAndExamDate(int pSemesterId, int pGroupNo, int pExamType, String pExamDate) {
    return getManager().deleteBySemesterGroupExamTypeAndExamDate(pSemesterId,pGroupNo,pExamType,pExamDate);
  }

  @Override
  public List<SeatPlan> getBySemesterGroupTypeRoomRowAndCol(int pSemesterId, int pGroupNo, int pType, int pRoomId, int pRow, int pCol) {
    return getManager().getBySemesterGroupTypeRoomRowAndCol(pSemesterId,pGroupNo,pType,pRoomId,pRow,pCol);
  }

  @Override
  public int checkIfExistsBySemesterGroupTypeRoomRowAndCol(int pSemesterId, int pGroupNo, int pType, int pRoomId, int pRow, int pCol) {
    return getManager().checkIfExistsBySemesterGroupTypeRoomRowAndCol(pSemesterId,pGroupNo,pType,pRoomId,pRow,pCol);
  }

  @Override
  public int checkIfExistsByRoomSemesterGroupExamType(int pRoomId, int pSemesterId, int pGroupNo, int pExamType) throws Exception{
    return getManager().checkIfExistsByRoomSemesterGroupExamType(pRoomId,pSemesterId,pGroupNo,pExamType);
  }

  @Override
  public List<SeatPlan> getBySemesterAndGroupAndExamTypeAndExamDate(int pSemesterId, int pGropuNo, int pExamType, String pExamDate) {
    return getManager().getBySemesterAndGroupAndExamTypeAndExamDate(pSemesterId,pGropuNo,pExamType,pExamDate);
  }

  @Override
  public int checkIfExistsBySemesterGroupTypeExamDateRoomRowAndCol(int pSemesterId, int pGroupNo, int pType, String pExamDate, int pRoomId, int pRow, int pCol) {
    return getManager().checkIfExistsBySemesterGroupTypeExamDateRoomRowAndCol(pSemesterId,pGroupNo,pType,pExamDate,pRoomId,pRow,pCol);
  }

  @Override
  public List<SeatPlan> getForStudent(String pStudentId, Integer pSemesterId) {
    return getManager().getForStudent(pStudentId,pSemesterId);
  }
}
