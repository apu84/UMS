package org.ums.cache;

import org.ums.domain.model.immutable.UgAdmissionLog;
import org.ums.domain.model.mutable.MutableUgAdmissionLog;
import org.ums.manager.CacheManager;
import org.ums.manager.UgAdmissionLogManager;
import org.ums.util.CacheUtil;

/**
 * Created by Monjur-E-Morshed on 01-Jan-17.
 */
public class UgAdmissionLogCache extends ContentCache<UgAdmissionLog, MutableUgAdmissionLog, Integer, UgAdmissionLogManager> implements UgAdmissionLogManager{

  private CacheManager<UgAdmissionLog, Integer> mCacheManager;

  public UgAdmissionLogCache(CacheManager<UgAdmissionLog, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<UgAdmissionLog, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(UgAdmissionLog.class, pId);
  }
}
