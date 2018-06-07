package org.ums.cache;

import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.ApplicationTesQuestions;
import org.ums.domain.model.mutable.MutableApplicationTES;
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
  @Override
  public List<MutableApplicationTES> getMigrationQuestions(Integer pSemesterId) {
    return getManager().getMigrationQuestions(pSemesterId);
  }

  @Override
  public List<MutableApplicationTES> getQuestions() {
    return getManager().getQuestions();
  }

  @Override
  public List<ApplicationTES> getAllQuestions(Integer pSemesterId) {
    return getManager().getAllQuestions(pSemesterId);
  }

  CacheManager<ApplicationTesQuestions, Long> mCacheManager;

  @Override
  protected CacheManager<ApplicationTesQuestions, Long> getCacheManager() {
    return mCacheManager;
  }

  public ApplicationTesQuestionCache(CacheManager<ApplicationTesQuestions, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

}
