package org.ums.cache.registrar;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.PublicationInformation;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.PublicationInformationManager;

import java.util.List;

public class PublicationInformationCache extends
    ContentCache<PublicationInformation, MutablePublicationInformation, Integer, PublicationInformationManager>
    implements PublicationInformationManager {

  private CacheManager<PublicationInformation, Integer> mCacheManager;

  public PublicationInformationCache(CacheManager<PublicationInformation, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<PublicationInformation, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public int savePublicationInformation(MutablePublicationInformation pMutablePublicationInformation) {
    return getManager().savePublicationInformation(pMutablePublicationInformation);
  }

  @Override
  public List<PublicationInformation> getEmployeePublicationInformation(int pEmployeeId) {
    return getManager().getEmployeePublicationInformation(pEmployeeId);
  }
}
