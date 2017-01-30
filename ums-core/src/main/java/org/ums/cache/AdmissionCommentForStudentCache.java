package org.ums.cache;

import org.ums.domain.model.immutable.AdmissionCommentForStudent;
import org.ums.domain.model.mutable.MutableAdmissionCommentForStudent;
import org.ums.manager.AdmissionCommentForStudentManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class AdmissionCommentForStudentCache
    extends
    ContentCache<AdmissionCommentForStudent, MutableAdmissionCommentForStudent, Integer, AdmissionCommentForStudentManager>
    implements AdmissionCommentForStudentManager {

  private CacheManager<AdmissionCommentForStudent, Integer> mCacheManager;

  public AdmissionCommentForStudentCache(
      CacheManager<AdmissionCommentForStudent, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public List<AdmissionCommentForStudent> getComments(int pSemesterId, String pReceiptId) {
    return getManager().getComments(pSemesterId, pReceiptId);
  }

  @Override
  public int saveComment(
      MutableAdmissionCommentForStudent pMutableAdmissionStudentsCertificateComment) {
    return getManager().saveComment(pMutableAdmissionStudentsCertificateComment);
  }

  @Override
  protected CacheManager<AdmissionCommentForStudent, Integer> getCacheManager() {
    return mCacheManager;
  }

}
