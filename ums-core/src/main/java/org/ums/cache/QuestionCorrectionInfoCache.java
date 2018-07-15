package org.ums.cache;

import org.ums.domain.model.immutable.QuestionCorrectionInfo;
import org.ums.domain.model.mutable.MutableQuestionCorrectionInfo;
import org.ums.manager.CacheManager;
import org.ums.manager.QuestionCorrectionManager;

/**
 * Created by Monjur-E-Morshed on 7/11/2018.
 */
public class QuestionCorrectionInfoCache extends
    ContentCache<QuestionCorrectionInfo, MutableQuestionCorrectionInfo, Long, QuestionCorrectionManager> implements
    QuestionCorrectionManager {

  CacheManager<QuestionCorrectionInfo, Long> mCacheManager;

  public QuestionCorrectionInfoCache(CacheManager<QuestionCorrectionInfo, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<QuestionCorrectionInfo, Long> getCacheManager() {
    return mCacheManager;
  }
}
