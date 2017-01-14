package org.ums.cache;

import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.immutable.AdmissionStudentCertificate;
import org.ums.domain.model.mutable.MutableAdmissionStudentCertificate;
import org.ums.manager.AdmissionStudentCertificateManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;
import java.util.List;

public class AdmissionStudentCertificateCache
    extends
    ContentCache<AdmissionStudentCertificate, MutableAdmissionStudentCertificate, Integer, AdmissionStudentCertificateManager>
    implements AdmissionStudentCertificateManager {

  private CacheManager<AdmissionStudentCertificate, Integer> mCacheManager;

  public AdmissionStudentCertificateCache(
      final CacheManager<AdmissionStudentCertificate, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<AdmissionStudentCertificate, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(AdmissionStudentCertificate.class, pId);
  }

  @Override
  public List<AdmissionStudentCertificate> getAdmissionStudentCertificateLists() {
    return getManager().getAdmissionStudentCertificateLists();
  }

}
