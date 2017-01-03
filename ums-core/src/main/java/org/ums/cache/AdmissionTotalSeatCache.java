package org.ums.cache;

import org.ums.domain.model.immutable.AdmissionTotalSeat;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;
import org.ums.manager.AdmissionTotalSeatManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
public class AdmissionTotalSeatCache extends ContentCache<AdmissionTotalSeat, MutableAdmissionTotalSeat, Integer, AdmissionTotalSeatManager> implements AdmissionTotalSeatManager {


  private CacheManager<AdmissionTotalSeat, Integer> mCacheManager;

  public AdmissionTotalSeatCache(CacheManager<AdmissionTotalSeat, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AdmissionTotalSeat, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(AdmissionTotalSeat.class, pId);
  }
}
