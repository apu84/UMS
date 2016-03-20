package org.ums.cache;

import org.ums.domain.model.immutable.ProgramType;
import org.ums.domain.model.mutable.MutableProgramType;
import org.ums.manager.CacheManager;
import org.ums.manager.ProgramTypeManager;
import org.ums.util.CacheUtil;

public class ProgramTypeCache extends ContentCache<ProgramType, MutableProgramType, Integer, ProgramTypeManager>
    implements ProgramTypeManager {
  private CacheManager<ProgramType> mCacheManager;

  public ProgramTypeCache(final CacheManager<ProgramType> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<ProgramType> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(ProgramType.class, pId);
  }
}
