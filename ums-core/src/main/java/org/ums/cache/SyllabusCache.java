package org.ums.cache;

import org.ums.domain.model.immutable.Syllabus;
import org.ums.domain.model.mutable.MutableSyllabus;
import org.ums.manager.CacheManager;
import org.ums.manager.SyllabusManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class SyllabusCache extends ContentCache<Syllabus, MutableSyllabus, String, SyllabusManager>
    implements SyllabusManager {
  private CacheManager<Syllabus, String> mCacheManager;

  public SyllabusCache(final CacheManager<Syllabus, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Syllabus, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(Syllabus.class, pId);
  }

  @Override
  public List<Syllabus> getSyllabusList(Integer pProgramId) {
    return getManager().getSyllabusList(pProgramId);
  }
}
