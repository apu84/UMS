package org.ums.cache;

import java.util.List;

import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;
import org.ums.manager.CacheManager;

import com.google.common.collect.Lists;

public class BearerAccessTokenCache extends
    ContentCache<BearerAccessToken, MutableBearerAccessToken, String, BearerAccessTokenManager> implements
    BearerAccessTokenManager {
  private CacheManager<BearerAccessToken, String> mCacheManager;

  public BearerAccessTokenCache(final CacheManager<BearerAccessToken, String> pBearerAccessTokenCacheManager) {
    mCacheManager = pBearerAccessTokenCacheManager;
  }

  @Override
  protected CacheManager<BearerAccessToken, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<BearerAccessToken> getByUser(String userId) {
    return getManager().getByUser(userId);
  }

  @Override
  public List<BearerAccessToken> getByAccessToken(String pId) {
    if(mCacheManager.get(getCacheKey(pId)) == null) {
      List<BearerAccessToken> token = getManager().getByAccessToken(pId);
      if(token.size() > 0) {
        mCacheManager.put(getCacheKey(pId), token.get(0));
      }
    }
    return Lists.newArrayList(mCacheManager.get(getCacheKey(pId)));
  }

  @Override
  public List<BearerAccessToken> getByRefreshToken(String pRefreshToken) {
    String cacheKey = getCacheKey(BearerAccessToken.class.toString(), pRefreshToken);
    return Lists.newArrayList(cachedEntity(cacheKey, () -> {
      List<BearerAccessToken> token = getManager().getByRefreshToken(pRefreshToken);
      return token.size() == 0 ? null : token.get(0);
    }));
  }
}
