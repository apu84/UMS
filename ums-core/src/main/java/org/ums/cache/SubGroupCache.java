package org.ums.cache;

import org.ums.domain.model.immutable.SubGroup;
import org.ums.domain.model.mutable.MutableSubGroup;
import org.ums.manager.CacheManager;
import org.ums.manager.SubGroupManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class SubGroupCache extends ContentCache<SubGroup, MutableSubGroup, Integer, SubGroupManager> implements
    SubGroupManager {

  CacheManager<SubGroup, Integer> mCacheManager;

  public SubGroupCache(CacheManager<SubGroup, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<SubGroup, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<SubGroup> getByGroupNo(int pGroupNo) {
    return getManager().getByGroupNo(pGroupNo);
  }

  @Override
  public int deleteBySemesterGroupAndType(int pSemesterId, int pGroupNo, int pType) {
    return getManager().deleteBySemesterGroupAndType(pSemesterId, pGroupNo, pType);
  }

  @Override
  public List<SubGroup> getBySemesterGroupNoAndType(int pSemesterId, int pGroupNo, int pType) {
    return getManager().getBySemesterGroupNoAndType(pSemesterId, pGroupNo, pType);
  }

  @Override
  public int getSubGroupNumberOfAGroup(int pSemesterId, int pExamType, int pGroupNo) {
    return getManager().getSubGroupNumberOfAGroup(pSemesterId, pExamType, pGroupNo);
  }

  @Override
  public List<SubGroup> getSubGroupMembers(int pSemesterId, int pExamTYpe, int pGroupNo, int pSubGroupNo) {
    return getManager().getSubGroupMembers(pSemesterId, pExamTYpe, pGroupNo, pSubGroupNo);
  }

  @Override
  public int checkBySemesterGroupNoAndType(int pSemesterId, int pGroupNo, int pType) {
    return getManager().checkBySemesterGroupNoAndType(pSemesterId, pGroupNo, pType);
  }

  @Override
  public int checkForHalfFinishedSubGroupsBySemesterGroupNoAndType(int pSemesterId, int pGroupNo, int pType) {
    return getManager().checkForHalfFinishedSubGroupsBySemesterGroupNoAndType(pSemesterId, pGroupNo, pType);
  }

  @Override
  public List<SubGroup> getBySemesterAndExamType(int pSemesterId, int pExamType) {
    return getManager().getBySemesterAndExamType(pSemesterId, pExamType);
  }
}
