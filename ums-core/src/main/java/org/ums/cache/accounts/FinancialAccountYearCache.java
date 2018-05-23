package org.ums.cache.accounts;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableFinancialAccountYear;
import org.ums.manager.CacheManager;
import org.ums.manager.accounts.FinancialAccountYearManager;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
public class FinancialAccountYearCache extends
    ContentCache<FinancialAccountYear, MutableFinancialAccountYear, Long, FinancialAccountYearManager> implements
    FinancialAccountYearManager {

  private CacheManager<FinancialAccountYear, Long> mFinancialAccountYearLongCacheManager;

  public FinancialAccountYearCache(CacheManager<FinancialAccountYear, Long> pFinancialAccountYearLongCacheManager) {
    mFinancialAccountYearLongCacheManager = pFinancialAccountYearLongCacheManager;
  }

  @Override
  protected CacheManager<FinancialAccountYear, Long> getCacheManager() {
    return mFinancialAccountYearLongCacheManager;
  }

  @Override
  public MutableFinancialAccountYear getOpenedFinancialAccountYear() {
    return getManager().getOpenedFinancialAccountYear();
  }

  @Override
  public void deleteAll() {
    getManager().deleteAll();
  }

  @Override
  public boolean exists(Date pStartDate, Date pEndDate) {
    return getManager().exists(pStartDate, pEndDate);
  }
}
