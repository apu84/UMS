package org.ums.cache.registrar;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.ServiceInformationDetail;
import org.ums.domain.model.mutable.registrar.MutableServiceInformationDetail;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.ServiceInformationDetailManager;

public class ServiceInformationDetailCache extends
    ContentCache<ServiceInformationDetail, MutableServiceInformationDetail, Integer, ServiceInformationDetailManager>
    implements ServiceInformationDetailManager {

  private CacheManager<ServiceInformationDetail, Integer> mCacheManager;

  public ServiceInformationDetailCache(CacheManager<ServiceInformationDetail, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<ServiceInformationDetail, Integer> getCacheManager() {
    return mCacheManager;
  }
}
