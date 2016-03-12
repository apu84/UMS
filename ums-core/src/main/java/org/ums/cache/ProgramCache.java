package org.ums.cache;

import org.ums.domain.model.mutable.MutableProgram;
import org.ums.domain.model.immutable.Program;
import org.ums.manager.CacheManager;
import org.ums.manager.ContentManager;
import org.ums.manager.ProgramManager;
import org.ums.util.CacheUtil;


public class ProgramCache extends ContentCache<Program, MutableProgram, Integer, ContentManager<Program, MutableProgram, Integer>> implements ProgramManager {
  private CacheManager<Program> mCacheManager;

  public ProgramCache(final CacheManager<Program> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(Program.class, pId);
  }
}
