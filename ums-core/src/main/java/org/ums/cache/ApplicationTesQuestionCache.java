package org.ums.cache;

import org.ums.domain.model.immutable.ApplicationTesQuestions;
import org.ums.domain.model.mutable.MutableApplicationTesQuestions;
import org.ums.manager.ApplicationTesQuestionManager;
import org.ums.manager.CacheManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 4/16/2018.
 */
public class ApplicationTesQuestionCache extends
    ContentCache<ApplicationTesQuestions, MutableApplicationTesQuestions, Long, ApplicationTesQuestionManager>
    implements ApplicationTesQuestionManager {

  CacheManager<ApplicationTesQuestions, Long> mCacheManager;

  @Override
  protected CacheManager<ApplicationTesQuestions, Long> getCacheManager() {
    return mCacheManager;
  }

  public ApplicationTesQuestionCache(CacheManager<ApplicationTesQuestions, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public List<ApplicationTesQuestions> getQuestionInfo(Integer pQuestionId) {
    return getManager().getQuestionInfo(pQuestionId);
  }
}
