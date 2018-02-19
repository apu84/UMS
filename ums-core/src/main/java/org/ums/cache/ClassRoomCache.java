package org.ums.cache;

import org.ums.domain.model.immutable.ClassRoom;
import org.ums.domain.model.mutable.MutableClassRoom;
import org.ums.manager.CacheManager;
import org.ums.manager.ClassRoomManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 19-Feb-17.
 */
public class ClassRoomCache extends ContentCache<ClassRoom, MutableClassRoom, Long, ClassRoomManager> implements
    ClassRoomManager {
  private CacheManager<ClassRoom, Long> mCacheManager;

  public ClassRoomCache(CacheManager<ClassRoom, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public List<ClassRoom> getRoomList() {
    return getManager().getRoomList();
  }

  @Override
  public ClassRoom getByRoomNo(String pRoomNo) {
    return getManager().getByRoomNo(pRoomNo);
  }

  @Override
  public List<ClassRoom> getSeatPlanRooms(Integer pSemesterid, Integer pExamType) {
    return getManager().getSeatPlanRooms(pSemesterid, pExamType);
  }

  @Override
  public List<ClassRoom> getRoomsBasedOnRoutine(int pSemesterId, int pProgramId) {
    return getManager().getRoomsBasedOnRoutine(pSemesterId, pProgramId);
  }

  @Override
  public List<ClassRoom> getAllForSeatPlan() {
    return getManager().getAllForSeatPlan();
  }

  @Override
  protected CacheManager<ClassRoom, Long> getCacheManager() {
    return mCacheManager;
  }
}
