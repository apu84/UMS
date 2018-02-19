package org.ums.cache.accounts;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.enums.accounts.definitions.group.GroupFlag;
import org.ums.manager.CacheManager;
import org.ums.manager.accounts.AccountManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
public class AccountCache extends ContentCache<Account, MutableAccount, Long, AccountManager> implements AccountManager {

  private CacheManager<Account, Long> mAccountLongCacheManager;

  public AccountCache(CacheManager<Account, Long> pAccountLongCacheManager) {
    mAccountLongCacheManager = pAccountLongCacheManager;
  }

  @Override
  protected CacheManager<Account, Long> getCacheManager() {
    return mAccountLongCacheManager;
  }

  @Override
  public Integer getSize() {
    return getManager().getSize();
  }

  @Override
  public List<Account> getAllPaginated(int itemPerPage, int pageNumber) {
    return getManager().getAllPaginated(itemPerPage, pageNumber);
  }

  @Override
  public List<Account> getAccounts(String pAccountName) {
    return getManager().getAccounts(pAccountName);
  }

  @Override
  public List<Account> getAccounts(GroupFlag pGroupFlag) {
    return getManager().getAccounts(pGroupFlag);
  }
}
