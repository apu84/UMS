package org.ums.bank.branch.user;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;

import java.util.List;

public class BranchUserCache extends ContentCache<BranchUser, MutableBranchUser, Long, BranchUserManager> implements
    BranchUserManager {
  private CacheManager<BranchUser, Long> mCacheManager;

  public BranchUserCache(CacheManager<BranchUser, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public BranchUser getByUserId(String pUserId) {
    String cacheKey = getCacheKey(BranchUser.class.toString(), pUserId);
    return cachedEntity(cacheKey, () -> getManager().getByUserId(pUserId));
  }

  @Override
  protected CacheManager<BranchUser, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<BranchUser> getUsersByBranch(Long pBranchId) {
    return getManager().getUsersByBranch(pBranchId);
  }

  @Override
  public BranchUser getUserByEmail(String pEmail) {
    String cacheKey = getCacheKey(BranchUser.class.toString(), pEmail);
    return cachedEntity(cacheKey, () -> getManager().getUserByEmail(pEmail));
  }
}
