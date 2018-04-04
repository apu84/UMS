package org.ums.bank.designation;

import org.ums.bank.designation.BankDesignation;
import org.ums.bank.designation.BankDesignationManager;
import org.ums.bank.designation.MutableBankDesignation;
import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;

public class BankDesignationCache extends
    ContentCache<BankDesignation, MutableBankDesignation, Long, BankDesignationManager> implements
    BankDesignationManager {
  private CacheManager<BankDesignation, Long> mCacheManager;

  public BankDesignationCache(CacheManager<BankDesignation, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public BankDesignation getByCode(String pCode) {
    String cacheKey = getCacheKey(BankDesignation.class.toString(), pCode);
    return cachedEntity(cacheKey, () -> getManager().getByCode(pCode));
  }

  @Override
  protected CacheManager<BankDesignation, Long> getCacheManager() {
    return mCacheManager;
  }
}
