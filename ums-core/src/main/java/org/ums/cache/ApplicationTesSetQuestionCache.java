package org.ums.cache;

import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.ApplicationTesSetQuestions;
import org.ums.domain.model.mutable.MutableApplicationTesSetQuestions;
import org.ums.manager.ApplicationTesSetQuestionManager;
import org.ums.manager.CacheManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public class ApplicationTesSetQuestionCache extends
    ContentCache<ApplicationTesSetQuestions, MutableApplicationTesSetQuestions, Long, ApplicationTesSetQuestionManager>
    implements ApplicationTesSetQuestionManager {
  @Override
  public List<ApplicationTES> getQuestionSemesterMap(Integer pSemesterId) {
    return getManager().getQuestionSemesterMap(pSemesterId);
  }

  CacheManager<ApplicationTesSetQuestions, Long> mCacheManager;

  @Override
  protected CacheManager<ApplicationTesSetQuestions, Long> getCacheManager() {
    return mCacheManager;
  }

  public ApplicationTesSetQuestionCache(CacheManager<ApplicationTesSetQuestions, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }
}
