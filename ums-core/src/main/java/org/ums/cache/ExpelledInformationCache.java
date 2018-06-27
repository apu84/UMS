package org.ums.cache;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.ExpelledInformation;
import org.ums.domain.model.mutable.MutableExpelledInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.ExpelledInformationManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 5/27/2018.
 */
public class ExpelledInformationCache extends
    ContentCache<ExpelledInformation, MutableExpelledInformation, Long, ExpelledInformationManager> implements
    ExpelledInformationManager {
  CacheManager<ExpelledInformation, Long> mCacheManager;

  public ExpelledInformationCache(CacheManager<ExpelledInformation, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<ExpelledInformation, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<ExpelledInformation> getSemesterExamTyeDateWiseRecords(Integer pSemesterId, Integer pExamType,
      String pExamDate) {
    return getManager().getSemesterExamTyeDateWiseRecords(pSemesterId, pExamType, pExamDate);
  }
}
