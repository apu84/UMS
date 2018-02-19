package org.ums.cache.library;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.immutable.library.Item;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.manager.CacheManager;
import org.ums.manager.library.ItemManager;

import java.util.List;

/**
 * Created by Ifti on 04-Mar-17.
 */
public class ItemCache extends ContentCache<Item, MutableItem, Long, ItemManager> implements ItemManager {

  CacheManager<Item, Long> mCacheManager;

  public ItemCache(CacheManager<Item, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Item, Long> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<Item> getByMfn(Long pMfn) {
    return getManager().getByMfn(pMfn);
  }

  @Override
  public Item getByAccessionNumber(String pAccessionNumber) {
    return getManager().getByAccessionNumber(pAccessionNumber);
  }

}
