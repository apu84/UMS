package org.ums.bank.branch;

import org.ums.cache.ContentCache;
import org.ums.manager.CacheManager;

import java.util.List;

public class BranchCache extends ContentCache<Branch, MutableBranch, Long, BranchManager> implements BranchManager {
  private CacheManager<Branch, Long> mCacheManager;

  public BranchCache(CacheManager<Branch, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  public Branch getByCode(String pCode) {
    String cacheKey = getCacheKey(Branch.class.toString(), pCode);
    return cachedEntity(cacheKey, () -> getManager().getByCode(pCode));
  }

  @Override
  protected CacheManager<Branch, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<Branch> getBranchesByBank(Long pBankId) {
    return getManager().getBranchesByBank(pBankId);
  }
}
