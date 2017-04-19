package org.ums.cache.registrar;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.ExperienceInformation;
import org.ums.domain.model.mutable.registrar.MutableExperienceInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.ExperienceInformationManager;

import java.util.List;

public class ExperienceInformationCache extends
    ContentCache<ExperienceInformation, MutableExperienceInformation, Integer, ExperienceInformationManager> implements
    ExperienceInformationManager {

  private CacheManager<ExperienceInformation, Integer> mCacheManager;

  public ExperienceInformationCache(CacheManager<ExperienceInformation, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<ExperienceInformation, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public int saveExperienceInformation(List<MutableExperienceInformation> pMutableExperienceInformation) {
    return getManager().saveExperienceInformation(pMutableExperienceInformation);
  }

  @Override
  public List<ExperienceInformation> getEmployeeExperienceInformation(String pEmployeeId) {
    return getManager().getEmployeeExperienceInformation(pEmployeeId);
  }
}
