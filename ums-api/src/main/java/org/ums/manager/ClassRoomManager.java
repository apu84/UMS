package org.ums.manager;

import org.ums.domain.model.immutable.ClassRoom;
import org.ums.domain.model.mutable.MutableClassRoom;

import java.util.List;

/**
 * Created by Ifti on 13-Feb-16.
 */
public interface ClassRoomManager extends ContentManager<ClassRoom, MutableClassRoom, Long> {
  List<ClassRoom> getRoomList();

  ClassRoom getByRoomNo(String pRoomNo);

  List<ClassRoom> getSeatPlanRooms(Integer pSemesterid, Integer pExamType);

  List<ClassRoom> getAllForSeatPlan();

  List<ClassRoom> getRoomsBasedOnRoutine(int pSemesterId, int pProgramId);
}
