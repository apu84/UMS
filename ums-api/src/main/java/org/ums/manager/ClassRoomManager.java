package org.ums.manager;

import org.ums.domain.model.mutable.MutableClassRoom;
import org.ums.domain.model.immutable.ClassRoom;

import java.util.List;

/**
 * Created by Ifti on 13-Feb-16.
 */
public interface ClassRoomManager extends ContentManager<ClassRoom, MutableClassRoom, Integer> {
  public List<ClassRoom> getRoomList();

  public ClassRoom getByRoomNo(String pRoomNo);

  public List<ClassRoom> getSeatPlanRooms(Integer pSemesterid, Integer pExamType);

  public List<ClassRoom> getRoomsBasedOnRoutine(int pSemesterId, int pProgramId);
}
