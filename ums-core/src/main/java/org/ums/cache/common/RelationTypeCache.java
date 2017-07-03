package org.ums.cache.common;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.common.RelationType;
import org.ums.domain.model.mutable.common.MutableRelationType;
import org.ums.manager.CacheManager;
import org.ums.manager.common.RelationTypeManager;

public class RelationTypeCache extends ContentCache<RelationType, MutableRelationType, Integer, RelationTypeManager>
    implements RelationTypeManager {

  CacheManager<RelationType, Integer> mCacheManager;

  public RelationTypeCache(CacheManager<RelationType, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<RelationType, Integer> getCacheManager() {
    return mCacheManager;
  }
}
