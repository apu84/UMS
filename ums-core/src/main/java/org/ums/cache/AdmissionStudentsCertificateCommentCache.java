package org.ums.cache;

import org.ums.domain.model.immutable.AdmissionStudentsCertificateComment;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateComment;
import org.ums.manager.AdmissionStudentsCertificateCommentManager;
import org.ums.manager.AdmissionStudentsCertificateHistoryManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by kawsu on 1/12/2017.
 */
public class AdmissionStudentsCertificateCommentCache
    extends
    ContentCache<AdmissionStudentsCertificateComment, MutableAdmissionStudentsCertificateComment, Integer, AdmissionStudentsCertificateCommentManager>
    implements AdmissionStudentsCertificateCommentManager {

  private CacheManager<AdmissionStudentsCertificateComment, Integer> mCacheManager;

  public AdmissionStudentsCertificateCommentCache(
      CacheManager<AdmissionStudentsCertificateComment, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public List<AdmissionStudentsCertificateComment> getComments(int pSemesterId, String pReceiptId) {
    return getManager().getComments(pSemesterId, pReceiptId);
  }

  @Override
  public int saveComment(
      MutableAdmissionStudentsCertificateComment pMutableAdmissionStudentsCertificateComment) {
    return getManager().saveComment(pMutableAdmissionStudentsCertificateComment);
  }

  @Override
  protected CacheManager<AdmissionStudentsCertificateComment, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(AdmissionStudentsCertificateComment.class, pId);
  }
}
