package org.ums.cache;

import org.ums.domain.model.immutable.Syllabus;
import org.ums.domain.model.mutable.MutableSyllabus;
import org.ums.manager.CacheManager;
import org.ums.manager.SyllabusManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class SyllabusCache extends ContentCache<Syllabus, MutableSyllabus, String, SyllabusManager> implements SyllabusManager {
  private CacheManager<Syllabus> mCacheManager;

  public SyllabusCache(final CacheManager<Syllabus> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Syllabus> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(Syllabus.class, pId);
  }

  @Override
  public List<Syllabus> getSyllabusList(Integer pProgramId) throws Exception {
    return getManager().getSyllabusList(pProgramId);
  }
}
