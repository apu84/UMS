package org.ums.decorator;

import org.ums.domain.model.immutable.ClassRoom;
import org.ums.domain.model.mutable.MutableClassRoom;
import org.ums.manager.ClassRoomManager;

import java.util.List;

/**
 * Created by Ifti on 13-Feb-16.
 */
public class ClassRoomDaoDecorator extends ContentDaoDecorator<ClassRoom, MutableClassRoom, Long, ClassRoomManager>
    implements ClassRoomManager {
  @Override
  public List<ClassRoom> getRoomList() {
    return getManager().getRoomList();
  }

  @Override
  public ClassRoom getByRoomNo(String pRoomNo) {
    return getManager().getByRoomNo(pRoomNo);
  }

  @Override
  public List<ClassRoom> getSeatPlanRooms(Integer pSemesterId, Integer pExamType) {
    return getManager().getSeatPlanRooms(pSemesterId, pExamType);
  }

  @Override
  public List<ClassRoom> getRoomsBasedOnRoutine(int pSemesterId, int pProgramId) {
    return getManager().getRoomsBasedOnRoutine(pSemesterId, pProgramId);
  }

  @Override
  public List<ClassRoom> getAllForSeatPlan() {
    return getManager().getAllForSeatPlan();
  }
}
