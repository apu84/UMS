package org.ums.cache;

import org.ums.domain.model.immutable.AdmissionCertificatesOfStudent;
import org.ums.domain.model.mutable.MutableAdmissionCertificatesOfStudent;
import org.ums.manager.AdmissionCertificatesOfStudentManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class AdmissionCertificatesOfStudentCache
    extends
    ContentCache<AdmissionCertificatesOfStudent, MutableAdmissionCertificatesOfStudent, Integer, AdmissionCertificatesOfStudentManager>
    implements AdmissionCertificatesOfStudentManager {

  private CacheManager<AdmissionCertificatesOfStudent, Integer> mCacheManager;

  public AdmissionCertificatesOfStudentCache(CacheManager<AdmissionCertificatesOfStudent, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public int saveAdmissionStudentsCertificates(List<MutableAdmissionCertificatesOfStudent> pCertificateHistorys) {
    return getManager().saveAdmissionStudentsCertificates(pCertificateHistorys);
  }

  @Override
  public List<AdmissionCertificatesOfStudent> getStudentsSavedCertificateLists(int pSemesterId, String pReceiptId) {
    return getManager().getStudentsSavedCertificateLists(pSemesterId, pReceiptId);
  }

  @Override
  protected CacheManager<AdmissionCertificatesOfStudent, Integer> getCacheManager() {
    return mCacheManager;
  }

}
