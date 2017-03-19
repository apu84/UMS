package org.ums.cache;

import org.ums.domain.model.immutable.AdmissionDeadline;
import org.ums.domain.model.mutable.MutableAdmissionDeadline;
import org.ums.manager.AdmissionDeadlineManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

/**
 * Created by Monjur-E-Morshed on 29-Dec-16.
 */
public class AdmissionDeadlineCache extends
    ContentCache<AdmissionDeadline, MutableAdmissionDeadline, Integer, AdmissionDeadlineManager> implements
    AdmissionDeadlineManager {

  private CacheManager<AdmissionDeadline, Integer> mCacheManager;

  public AdmissionDeadlineCache(CacheManager<AdmissionDeadline, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AdmissionDeadline, Integer> getCacheManager() {
    return mCacheManager;
  }

}
