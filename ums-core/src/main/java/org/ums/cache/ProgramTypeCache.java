package org.ums.cache;

import org.ums.domain.model.immutable.ProgramType;
import org.ums.domain.model.mutable.MutableProgramType;
import org.ums.manager.CacheManager;
import org.ums.manager.ProgramTypeManager;
import org.ums.util.CacheUtil;

public class ProgramTypeCache extends ContentCache<ProgramType, MutableProgramType, Integer, ProgramTypeManager>
    implements ProgramTypeManager {
  private CacheManager<ProgramType, Integer> mCacheManager;

  public ProgramTypeCache(final CacheManager<ProgramType, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<ProgramType, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(ProgramType.class, pId);
  }
}
