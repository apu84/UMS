package org.ums.cache;

import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;
import org.ums.manager.CacheManager;
import org.ums.util.CacheUtil;


public class BearerAccessTokenCache extends ContentCache<BearerAccessToken, MutableBearerAccessToken, String, BearerAccessTokenManager>
    implements BearerAccessTokenManager {
  private CacheManager<BearerAccessToken, String> mCacheManager;

  public BearerAccessTokenCache(final CacheManager<BearerAccessToken, String> pBearerAccessTokenCacheManager) {
    mCacheManager = pBearerAccessTokenCacheManager;
  }

  @Override
  protected CacheManager<BearerAccessToken, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(BearerAccessToken.class, pId);
  }

  @Override
  public BearerAccessToken getByUser(String userId) {
    return getManager().getByUser(userId);
  }
}
