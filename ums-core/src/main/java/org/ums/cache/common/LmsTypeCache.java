package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.LmsType;
import org.ums.domain.model.mutable.common.MutableLmsType;
import org.ums.enums.common.EmployeeLeaveType;
import org.ums.enums.common.Gender;
import org.ums.manager.CacheManager;
import org.ums.manager.common.LmsTypeManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 03-May-17.
 */
public class LmsTypeCache extends ContentCache<LmsType, MutableLmsType, Integer, LmsTypeManager> implements
    LmsTypeManager {

  private CacheManager<LmsType, Integer> mCacheManager;

  public LmsTypeCache(CacheManager<LmsType, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<LmsType, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<LmsType> getLmsTypes(EmployeeLeaveType pType, Gender pGender) {
    return getManager().getLmsTypes(pType, pGender);
  }
}
