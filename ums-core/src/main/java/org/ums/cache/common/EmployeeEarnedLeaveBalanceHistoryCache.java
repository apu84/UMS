package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalanceHistory;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalanceHistory;
import org.ums.manager.CacheManager;
import org.ums.manager.common.EmployeeEarnedLeaveBalanceHistoryManager;

import java.util.List;

public class EmployeeEarnedLeaveBalanceHistoryCache
    extends
    ContentCache<EmployeeEarnedLeaveBalanceHistory, MutableEmployeeEarnedLeaveBalanceHistory, Long, EmployeeEarnedLeaveBalanceHistoryManager>
    implements EmployeeEarnedLeaveBalanceHistoryManager {

  private CacheManager<EmployeeEarnedLeaveBalanceHistory, Long> mCacheManager;

  public EmployeeEarnedLeaveBalanceHistoryCache(CacheManager<EmployeeEarnedLeaveBalanceHistory, Long> mCacheManager) {
    this.mCacheManager = mCacheManager;
  }

  @Override
  protected CacheManager<EmployeeEarnedLeaveBalanceHistory, Long> getCacheManager() {
    // TODO Auto-generated method stub
    return mCacheManager;
  }

  @Override
  public List<EmployeeEarnedLeaveBalanceHistory> getAllEarnedLeaveBalanceHistoryOfActiveEmployees() {
    return getManager().getAllEarnedLeaveBalanceHistoryOfActiveEmployees();
  }
}
