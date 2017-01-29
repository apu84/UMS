package org.ums.cache;

import org.springframework.cache.Cache;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.mutable.MutableParameterSetting;
import org.ums.manager.CacheManager;
import org.ums.manager.ParameterSettingManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by My Pc on 3/15/2016.
 */
public class ParameterSettingCache extends
    ContentCache<ParameterSetting, MutableParameterSetting, String, ParameterSettingManager>
    implements ParameterSettingManager {
  private CacheManager<ParameterSetting, String> mCacheManager;

  public ParameterSettingCache(final CacheManager<ParameterSetting, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public ParameterSetting getByParameterAndSemesterId(String parameter, int semesterId) {
    return getManager().getByParameterAndSemesterId(parameter, semesterId);
  }

  @Override
  public List<ParameterSetting> getBySemester(int semesterId) {
    return getManager().getBySemester(semesterId);
  }

  @Override
  protected CacheManager<ParameterSetting, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public ParameterSetting getBySemesterAndParameterId(int parameterId, int semesterId) {
    return getManager().getBySemesterAndParameterId(parameterId, semesterId);
  }
}
