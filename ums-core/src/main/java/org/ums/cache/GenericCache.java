package org.ums.cache;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.enums.accounts.definitions.group.GroupFlag;
import org.ums.enums.common.AscendingOrDescendingType;
import org.ums.manager.CacheManager;
import org.ums.manager.accounts.AccountManager;

import java.util.List;

public class GenericCache extends ContentCache<Account, MutableAccount, Long, AccountManager> {

  private CacheManager<Account, Long> mAccountLongCacheManager;

  public GenericCache(CacheManager<Account, Long> pAccountLongCacheManager) {
    mAccountLongCacheManager = pAccountLongCacheManager;
  }

  @Override
  protected CacheManager<Account, Long> getCacheManager() {
    return mAccountLongCacheManager;
  }

  public Object getObject(String cacheKey) {
    return getCacheManager().getObject(cacheKey);
  }

  public void putObject(String cacheKey, Object cacheValue) {
    getCacheManager().putObject(cacheKey, cacheValue);
  }

}
