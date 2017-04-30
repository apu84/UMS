package org.ums.cache;

import org.ums.domain.model.immutable.DepartmentSelectionDeadline;
import org.ums.domain.model.mutable.MutableDepartmentSelectionDeadline;
import org.ums.manager.CacheManager;
import org.ums.manager.DepartmentSelectionDeadlineManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 27-Apr-17.
 */
public class DepartmentSelectionDeadlineCache
    extends
    ContentCache<DepartmentSelectionDeadline, MutableDepartmentSelectionDeadline, Integer, DepartmentSelectionDeadlineManager>
    implements DepartmentSelectionDeadlineManager {

  private CacheManager<DepartmentSelectionDeadline, Integer> mCacheManager;

  public DepartmentSelectionDeadlineCache(CacheManager<DepartmentSelectionDeadline, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<DepartmentSelectionDeadline, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<DepartmentSelectionDeadline> getDeadline(int pSemesterId, String pUnit, String pQuota) {
    return getManager().getDeadline(pSemesterId, pUnit, pQuota);
  }
}
