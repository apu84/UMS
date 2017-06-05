package org.ums.cache.registrar;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.AwardInformation;
import org.ums.domain.model.mutable.registrar.MutableAwardInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.AwardInformationManager;

import java.util.List;

public class AwardInformationCache extends
    ContentCache<AwardInformation, MutableAwardInformation, Integer, AwardInformationManager> implements
    AwardInformationManager {

  private CacheManager<AwardInformation, Integer> mCacheManager;

  public AwardInformationCache(CacheManager<AwardInformation, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AwardInformation, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public int saveAwardInformation(List<MutableAwardInformation> pMutableAwardInformation) {
    return getManager().saveAwardInformation(pMutableAwardInformation);
  }

  @Override
  public List<AwardInformation> getEmployeeAwardInformation(String pEmployeeId) {
    return getManager().getEmployeeAwardInformation(pEmployeeId);
  }

  @Override
  public int deleteAwardInformation(String pEmployeeId) {
    return getManager().deleteAwardInformation(pEmployeeId);
  }

  @Override
  public int updateAwardInformation(List<MutableAwardInformation> pAwardInformation) {
    return getManager().updateAwardInformation(pAwardInformation);
  }

  @Override
  public int deleteAwardInformation(List<MutableAwardInformation> pAwardInformation) {
    return getManager().deleteAwardInformation(pAwardInformation);
  }
}
