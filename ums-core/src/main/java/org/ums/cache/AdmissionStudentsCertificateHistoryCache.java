package org.ums.cache;

import org.ums.domain.model.immutable.AdmissionStudentsCertificateHistory;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateHistory;
import org.ums.manager.AdmissionStudentsCertificateHistoryManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by kawsu on 1/11/2017.
 */
public class AdmissionStudentsCertificateHistoryCache
    extends
    ContentCache<AdmissionStudentsCertificateHistory, MutableAdmissionStudentsCertificateHistory, Integer, AdmissionStudentsCertificateHistoryManager>
    implements AdmissionStudentsCertificateHistoryManager {

  private CacheManager<AdmissionStudentsCertificateHistory, Integer> mCacheManager;

  public AdmissionStudentsCertificateHistoryCache(
      CacheManager<AdmissionStudentsCertificateHistory, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public int saveAdmissionStudentsCertificates(
      List<MutableAdmissionStudentsCertificateHistory> pCertificateHistorys) {
    return getManager().saveAdmissionStudentsCertificates(pCertificateHistorys);
  }

  @Override
  public List<AdmissionStudentsCertificateHistory> getStudentsSavedCertificateLists(
      int pSemesterId, String pReceiptId) {
    return getManager().getStudentsSavedCertificateLists(pSemesterId, pReceiptId);
  }

  @Override
  protected CacheManager<AdmissionStudentsCertificateHistory, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(AdmissionStudentsCertificateHistory.class, pId);
  }
}
