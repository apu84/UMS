package org.ums.cache;


import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.immutable.Semester;
import org.ums.enums.ProgramType;
import org.ums.enums.SemesterStatus;
import org.ums.manager.CacheManager;
import org.ums.manager.SemesterManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class SemesterCache extends ContentCache<Semester, MutableSemester, Integer, SemesterManager> implements SemesterManager {
  private CacheManager<Semester, Integer> mCacheManager;

  public SemesterCache(final CacheManager<Semester, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Semester, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(Semester.class, pId);
  }

  @Override
  public List<Semester> getSemesters(Integer pProgramType, Integer pLimit) throws Exception {
    return getManager().getSemesters(pProgramType, pLimit);
  }

  @Override
  public Semester getPreviousSemester(Integer pSemesterId, Integer pProgramTypeId) throws Exception {
    return getManager().getPreviousSemester(pSemesterId, pProgramTypeId);
  }

  @Override
  public Semester getSemesterByStatus(ProgramType pProgramType,SemesterStatus status) throws Exception {
    return getManager().getSemesterByStatus(pProgramType,status);
  }
}
