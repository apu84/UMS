package org.ums.cache;

import org.ums.domain.model.immutable.AdmissionAllTypesOfCertificate;
import org.ums.domain.model.mutable.MutableAdmissionAllTypesOfCertificate;
import org.ums.manager.AdmissionAllTypesOfCertificateManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;
import java.util.List;

public class AdmissionAllTypesOfCertificateCache
    extends
    ContentCache<AdmissionAllTypesOfCertificate, MutableAdmissionAllTypesOfCertificate, Integer, AdmissionAllTypesOfCertificateManager>
    implements AdmissionAllTypesOfCertificateManager {

  private CacheManager<AdmissionAllTypesOfCertificate, Integer> mCacheManager;

  public AdmissionAllTypesOfCertificateCache(final CacheManager<AdmissionAllTypesOfCertificate, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AdmissionAllTypesOfCertificate, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<AdmissionAllTypesOfCertificate> getAdmissionStudentCertificateLists() {
    return getManager().getAdmissionStudentCertificateLists();
  }

  @Override
  public List<AdmissionAllTypesOfCertificate> getCertificates(String pCertificateType) {
    return getManager().getCertificates(pCertificateType);
  }

}
