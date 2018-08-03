package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalance;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalance;
import org.ums.manager.CacheManager;
import org.ums.manager.common.EmployeeEarnedLeaveBalanceManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 26-Jul-18.
 */
public class EmployeeEarnedLeaveBalanceCache
    extends
    ContentCache<EmployeeEarnedLeaveBalance, MutableEmployeeEarnedLeaveBalance, Long, EmployeeEarnedLeaveBalanceManager>
    implements EmployeeEarnedLeaveBalanceManager {
  CacheManager<EmployeeEarnedLeaveBalance, Long> mEmployeeEarnedLeaveBalanceLongCacheManager;

  public EmployeeEarnedLeaveBalanceCache(
      CacheManager<EmployeeEarnedLeaveBalance, Long> pEmployeeEarnedLeaveBalanceLongCacheManager) {
    mEmployeeEarnedLeaveBalanceLongCacheManager = pEmployeeEarnedLeaveBalanceLongCacheManager;
  }

  @Override
  protected CacheManager<EmployeeEarnedLeaveBalance, Long> getCacheManager() {
    return mEmployeeEarnedLeaveBalanceLongCacheManager;
  }

  @Override
  public EmployeeEarnedLeaveBalance getEarnedLeaveBalance(String pEmployeeId) {
    return getManager().getEarnedLeaveBalance(pEmployeeId);
  }

  @Override
  public List<EmployeeEarnedLeaveBalance> getAllEarnedLeaveBalanceOfActiveEmployees() {
    return getManager().getAllEarnedLeaveBalanceOfActiveEmployees();
  }
}
