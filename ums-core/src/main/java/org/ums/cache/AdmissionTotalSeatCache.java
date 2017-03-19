package org.ums.cache;

import org.ums.domain.model.immutable.AdmissionTotalSeat;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;
import org.ums.manager.AdmissionTotalSeatManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
public class AdmissionTotalSeatCache extends
    ContentCache<AdmissionTotalSeat, MutableAdmissionTotalSeat, Integer, AdmissionTotalSeatManager> implements
    AdmissionTotalSeatManager {

  private CacheManager<AdmissionTotalSeat, Integer> mCacheManager;

  public AdmissionTotalSeatCache(CacheManager<AdmissionTotalSeat, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AdmissionTotalSeat, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<AdmissionTotalSeat> getAdmissionTotalSeat(int pSemesterId, ProgramType pProgramType, QuotaType pQuotaType) {
    return getManager().getAdmissionTotalSeat(pSemesterId, pProgramType, pQuotaType);
  }
}
