package org.ums.microservice.instance.cachewarmer;

import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.cache.CacheFactory;
import org.ums.configuration.UMSConfiguration;
import org.ums.manager.CacheManager;
import org.ums.manager.CacheWarmerManager;
import org.ums.manager.ContentManager;
import org.ums.microservice.AbstractService;

public class CacheWarmerManagerImpl extends AbstractService implements CacheWarmerManager {
  private static final Logger mLogger = LoggerFactory.getLogger(CacheWarmerManagerImpl.class);

  UMSConfiguration mUMSConfiguration;

  SecurityManager mSecurityManager;

  private ContentManager[] mContentManagers;

  private CacheFactory mCacheFactory;

  @Override
  public void warm() {
    if(login()) {
      CacheManager cacheManager = mCacheFactory.getCacheManager();
      try {
        if(mContentManagers.length > 0) {
          // start warming up
          mLogger.info("Started warming up cache");
          for(ContentManager contentManager : mContentManagers) {
            contentManager.getAll();
          }
        }
        mLogger.info("Cache warm up finish");
      } catch(Exception e) {
        mLogger.error("Failed to warm up cache properly, will now fallback to initial state", e);
      }
    }
  }

  public CacheWarmerManagerImpl(SecurityManager pSecurityManager, CacheFactory pCacheFactory,
      UMSConfiguration pUMSConfiguration, ContentManager... pContentManagers) {
    mSecurityManager = pSecurityManager;
    mContentManagers = pContentManagers;
    mCacheFactory = pCacheFactory;
    mUMSConfiguration = pUMSConfiguration;
  }

  @Override
  public void start() {
    warm();
  }

  @Override
  protected SecurityManager getSecurityManager() {
    return mSecurityManager;
  }

  @Override
  protected UMSConfiguration getUMSConfiguration() {
    return mUMSConfiguration;
  }
}
