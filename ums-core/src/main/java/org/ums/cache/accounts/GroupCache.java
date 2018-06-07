package org.ums.cache.accounts;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.manager.CacheManager;
import org.ums.manager.accounts.GroupManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Mar-18.
 */
public class GroupCache extends ContentCache<Group, MutableGroup, Long, GroupManager> implements GroupManager {

  private CacheManager<Group, Long> mGroupCacheManager;

  public GroupCache(CacheManager<Group, Long> pGroupCacheManager) {
    mGroupCacheManager = pGroupCacheManager;
  }

  @Override
  protected CacheManager<Group, Long> getCacheManager() {
    return mGroupCacheManager;
  }

  @Override
  public List<Group> getByMainGroup(Group pGroup) {
    return getManager().getByMainGroup(pGroup);
  }

  @Override
  public List<Group> getExcludingMainGroupList(List<String> pMainGroupCodeList) {
    return getManager().getExcludingMainGroupList(pMainGroupCodeList);
  }

  @Override
  public List<Group> getIncludingMainGroupList(List<String> pMainGroupCodeList) {
    return getManager().getIncludingMainGroupList(pMainGroupCodeList);
  }
}
