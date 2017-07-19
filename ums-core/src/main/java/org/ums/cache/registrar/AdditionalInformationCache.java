package org.ums.cache.registrar;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.AdditionalInformation;
import org.ums.domain.model.mutable.registrar.MutableAdditionalInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.AdditionalInformationManager;

public class AdditionalInformationCache extends
    ContentCache<AdditionalInformation, MutableAdditionalInformation, String, AdditionalInformationManager> implements
    AdditionalInformationManager {

  private CacheManager<AdditionalInformation, String> mCacheManager;

  public AdditionalInformationCache(CacheManager<AdditionalInformation, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AdditionalInformation, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public int saveAdditionalInformation(MutableAdditionalInformation pMutableAdditionalInformation) {
    return getManager().saveAdditionalInformation(pMutableAdditionalInformation);
  }

  @Override
  public AdditionalInformation getAdditionalInformation(String pEmployeeId) {
    return getManager().getAdditionalInformation(pEmployeeId);
  }

  @Override
  public int deleteAdditionalInformation(MutableAdditionalInformation pMutableAdditionalInformation) {
    return getManager().deleteAdditionalInformation(pMutableAdditionalInformation);
  }

  @Override
  public int updateAdditionalInformation(MutableAdditionalInformation pMutableAdditionalInformation) {
    return getManager().updateAdditionalInformation(pMutableAdditionalInformation);
  }
}
