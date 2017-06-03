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
  public int savePublicationInformation(List<MutablePublicationInformation> pMutablePublicationInformation) {
    return getManager().savePublicationInformation(pMutablePublicationInformation);
  }

  @Override
  public List<PublicationInformation> getEmployeePublicationInformation(String pEmployeeId) {
    return getManager().getEmployeePublicationInformation(pEmployeeId);
  }

  @Override
  public List<PublicationInformation> getPublicationInformationWithPagination(String pEmployeeId,
      String pPublicationStatus, int pPageNumber, int pItemPerPage) {
    return getManager().getPublicationInformationWithPagination(pEmployeeId, pPublicationStatus, pPageNumber,
        pItemPerPage);
  }

  @Override
  public List<PublicationInformation> getEmployeePublicationInformation(String pEmployeeId, String pStatus) {
    return getManager().getEmployeePublicationInformation(pEmployeeId, pStatus);
  }

  @Override
  public List<PublicationInformation> getPublicationInformation(String pPublicationStatus) {
    return getManager().getPublicationInformation(pPublicationStatus);
  }

  @Override
  public int updatePublicationStatus(MutablePublicationInformation pMutablePublicationInformation) {
    return getManager().updatePublicationStatus(pMutablePublicationInformation);
  }

  @Override
  public int deletePublicationInformation(String pEmployeeId) {
    return getManager().deletePublicationInformation(pEmployeeId);
  }
}
