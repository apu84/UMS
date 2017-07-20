package org.ums.cache.registrar;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.domain.model.mutable.registrar.MutableServiceInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.ServiceInformationManager;

import java.util.List;

public class ServiceInformationCache extends
    ContentCache<ServiceInformation, MutableServiceInformation, Long, ServiceInformationManager> implements
    ServiceInformationManager {

  private CacheManager<ServiceInformation, Long> mCacheManager;

  public ServiceInformationCache(CacheManager<ServiceInformation, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<ServiceInformation, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public Long saveServiceInformation(MutableServiceInformation pMutableServiceInformation) {
    return getManager().saveServiceInformation(pMutableServiceInformation);
  }

  @Override
  public List<ServiceInformation> getServiceInformation(String pEmployeeId) {
    return getManager().getServiceInformation(pEmployeeId);
  }

  @Override
  public int updateServiceInformation(MutableServiceInformation pMutableServiceInformation) {
    return getManager().updateServiceInformation(pMutableServiceInformation);
  }

  @Override
  public int deleteServiceInformation(MutableServiceInformation pMutableServiceInformation) {
    return getManager().deleteServiceInformation(pMutableServiceInformation);
  }
}
