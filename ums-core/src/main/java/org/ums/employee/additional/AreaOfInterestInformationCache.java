package org.ums.employee.additional;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;

import java.util.List;

public class AreaOfInterestInformationCache extends
    ContentCache<AreaOfInterestInformation, MutableAreaOfInterestInformation, String, AreaOfInterestInformationManager>
    implements AreaOfInterestInformationManager {
  private CacheManager<AreaOfInterestInformation, String> mCacheManager;

  public AreaOfInterestInformationCache(CacheManager<AreaOfInterestInformation, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public List<AreaOfInterestInformation> getAreaOfInterestInformation(String pEmployeeId) {
    return getManager().getAreaOfInterestInformation(pEmployeeId);
  }

  @Override
  public int saveAreaOfInterestInformation(List<MutableAreaOfInterestInformation> pMutableAreaOfInterestInformation) {
    return getManager().saveAreaOfInterestInformation(pMutableAreaOfInterestInformation);
  }

  @Override
  public int updateAreaOfInformation(List<MutableAreaOfInterestInformation> pMutableAreaOfInterestInformation) {
    return getManager().updateAreaOfInformation(pMutableAreaOfInterestInformation);
  }

  @Override
  public int deleteAreaOfInterestInformation(String pEmployeeId) {
    return getManager().deleteAreaOfInterestInformation(pEmployeeId);
  }

  @Override
  protected CacheManager<AreaOfInterestInformation, String> getCacheManager() {
    return mCacheManager;
  }
}
