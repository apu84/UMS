package org.ums.cache.registrar;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.domain.model.mutable.registrar.MutableServiceInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.ServiceInformationManager;

import java.util.List;

public class ServiceInformationCache extends
    ContentCache<ServiceInformation, MutableServiceInformation, Integer, ServiceInformationManager> implements
    ServiceInformationManager {

  private CacheManager<ServiceInformation, Integer> mCacheManager;

  public ServiceInformationCache(CacheManager<ServiceInformation, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<ServiceInformation, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public int saveServiceInformation(List<MutableServiceInformation> pMutableEmployeeInformation) {
    return getManager().saveServiceInformation(pMutableEmployeeInformation);
  }

  @Override
  public List<ServiceInformation> getServiceInformation(int pEmployeeId) {
    return getManager().getServiceInformation(pEmployeeId);
  }
}
