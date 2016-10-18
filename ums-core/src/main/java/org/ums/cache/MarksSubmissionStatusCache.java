package org.ums.cache;

import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.enums.ExamType;
import org.ums.manager.CacheManager;
import org.ums.manager.MarksSubmissionStatusManager;
import org.ums.util.CacheUtil;

public class MarksSubmissionStatusCache
    extends ContentCache<MarksSubmissionStatus, MutableMarksSubmissionStatus, Integer, MarksSubmissionStatusManager>
    implements MarksSubmissionStatusManager {

  private CacheManager<MarksSubmissionStatus, Integer> mCacheManager;

  public MarksSubmissionStatusCache(CacheManager<MarksSubmissionStatus, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<MarksSubmissionStatus, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(MarksSubmissionStatus.class, pId);
  }

  @Override
  public MarksSubmissionStatus get(Integer pSemesterId, String pCourseId, ExamType pExamType) throws Exception {
    String cacheKey = getCacheKey(MarksSubmissionStatus.class.toString(), pSemesterId, pCourseId, pExamType);
    return cachedEntity(cacheKey, () -> getManager().get(pSemesterId, pCourseId, pExamType));
  }
}
