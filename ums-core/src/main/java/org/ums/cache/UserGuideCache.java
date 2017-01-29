package org.ums.cache;

import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.immutable.UserGuide;
import org.ums.domain.model.mutable.MutableUserGuide;
import org.ums.manager.CacheManager;
import org.ums.manager.UserGuideManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class UserGuideCache extends
    ContentCache<UserGuide, MutableUserGuide, Integer, UserGuideManager> implements
    UserGuideManager {

  private CacheManager<UserGuide, Integer> mCacheManager;

  public UserGuideCache(CacheManager<UserGuide, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<UserGuide, Integer> getCacheManager() {
    return mCacheManager;
  }

  public List<UserGuide> getUserGuideList(Integer pRoleId,String pUserId) {
    String cacheKey = getCacheKey(UserGuide.class.toString(), pUserId);
    return cachedList(cacheKey, () -> getManager().getUserGuideList(pRoleId,pUserId));
  }

  public UserGuide getUserGuide(Integer pNavigationId) {
    String cacheKey = getCacheKey(UserGuide.class.toString(), pNavigationId);
    return cachedEntity(cacheKey, () -> getManager().getUserGuide(pNavigationId));
  }
}
