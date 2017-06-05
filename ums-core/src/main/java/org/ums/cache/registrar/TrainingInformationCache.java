package org.ums.cache.registrar;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.registrar.TrainingInformation;
import org.ums.domain.model.mutable.registrar.MutableTrainingInformation;
import org.ums.manager.CacheManager;
import org.ums.manager.registrar.TrainingInformationManager;

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
  public int saveTrainingInformation(List<MutableTrainingInformation> pMutableTrainingInformation) {
    return getManager().saveTrainingInformation(pMutableTrainingInformation);
  }

  @Override
  public List<TrainingInformation> getEmployeeTrainingInformation(String pEmployeeId) {
    return getManager().getEmployeeTrainingInformation(pEmployeeId);
  }

  @Override
  public int deleteTrainingInformation(String pEmployeeId) {
    return getManager().deleteTrainingInformation(pEmployeeId);
  }

  @Override
  public int updateTrainingInformation(List<MutableTrainingInformation> pMutableTrainingInformation) {
    return getManager().updateTrainingInformation(pMutableTrainingInformation);
  }

  @Override
  public int deleteTrainingInformation(List<MutableTrainingInformation> pMutableTrainingInformation) {
    return getManager().deleteTrainingInformation(pMutableTrainingInformation);
  }

}
