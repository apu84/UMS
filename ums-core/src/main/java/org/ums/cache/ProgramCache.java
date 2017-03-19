package org.ums.cache;

import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.mutable.MutableProgram;
import org.ums.manager.CacheManager;
import org.ums.manager.ProgramManager;
import org.ums.util.CacheUtil;

public class ProgramCache extends ContentCache<Program, MutableProgram, Integer, ProgramManager> implements
    ProgramManager {
  private CacheManager<Program, Integer> mCacheManager;

  public ProgramCache(final CacheManager<Program, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Program, Integer> getCacheManager() {
    return mCacheManager;
  }

}
