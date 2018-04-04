package org.ums.bank;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;

public class BankCache extends ContentCache<Bank, MutableBank, Long, BankManager> implements BankManager {
  private CacheManager<Bank, Long> mCacheManager;

  public BankCache(CacheManager<Bank, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public Bank getByCode(String pCode) {
    String cacheKey = getCacheKey(Bank.class.toString(), pCode);
    return cachedEntity(cacheKey, () -> getManager().getByCode(pCode));
  }

  @Override
  protected CacheManager<Bank, Long> getCacheManager() {
    return mCacheManager;
  }
}
