package org.ums.cache.registrar.employee;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.employee.TrainingInformation;
import org.ums.domain.model.mutable.registrar.employee.MutableTrainingInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.employee.TrainingInformationManager;

import java.util.List;

public class TrainingInformationCache extends
    ContentCache<TrainingInformation, MutableTrainingInformation, Integer, TrainingInformationManager> implements
    TrainingInformationManager {

  private CacheManager<TrainingInformation, Integer> mCacheManager;

  public TrainingInformationCache(CacheManager<TrainingInformation, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<TrainingInformation, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public int saveTrainingInformation(MutableTrainingInformation pMutableTrainingInformation) {
    return getManager().saveTrainingInformation(pMutableTrainingInformation);
  }

  @Override
  public List<TrainingInformation> getEmployeeTrainingInformation(int pEmployeeId) {
    return getManager().getEmployeeTrainingInformation(pEmployeeId);
  }
}
