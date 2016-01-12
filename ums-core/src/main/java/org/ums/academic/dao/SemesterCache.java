package org.ums.academic.dao;


import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.regular.Semester;
import org.ums.manager.CacheManager;
import org.ums.manager.SemesterManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class SemesterCache extends ContentCache<Semester, MutableSemester, Integer, SemesterManager> implements SemesterManager {
  private CacheManager<Semester> mCacheManager;
  private SemesterManager mSemesterManager;

  public SemesterCache(final CacheManager<Semester> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(Semester.class, pId);
  }

  public SemesterManager getManager() {
    return mSemesterManager;
  }

  public void setManager(SemesterManager pManager) {
    mSemesterManager = pManager;
  }

  @Override
  public List<Semester> getSemesters(Integer pProgramType, Integer pLimit) throws Exception {
    return getManager().getSemesters(pProgramType, pLimit);
  }
}
