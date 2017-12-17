package org.ums.cache.library;

import org.ums.cache.ContentCache;
import org.ums.domain.model.immutable.library.Checkout;
import org.ums.domain.model.mutable.library.MutableCheckout;
import org.ums.manager.CacheManager;
import org.ums.manager.library.CheckoutManager;

import java.util.List;

public class CheckoutCache extends ContentCache<Checkout, MutableCheckout, Long, CheckoutManager> implements
    CheckoutManager {

  CacheManager<Checkout, Long> mCacheManager;

  public CheckoutCache(CacheManager<Checkout, Long> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Checkout, Long> getCacheManager() {
    return mCacheManager;
  }

}
