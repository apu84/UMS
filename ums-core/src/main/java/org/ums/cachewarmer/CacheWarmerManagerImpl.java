package org.ums.cachewarmer;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.cache.CacheFactory;
import org.ums.configuration.KafkaProducerConfig;
import org.ums.configuration.UMSConfiguration;
import org.ums.manager.CacheManager;
import org.ums.manager.CacheWarmerManager;
import org.ums.manager.ContentManager;

public class CacheWarmerManagerImpl implements CacheWarmerManager {
  private static final Logger mLogger = LoggerFactory.getLogger(CacheWarmerManagerImpl.class);

  UMSConfiguration mUMSConfiguration;

  SecurityManager mSecurityManager;

  private CacheWarmer mCacheWarmer;

  private ContentManager[] mContentManagers;

  private CacheFactory mCacheFactory;

  @Override
  public void warm() {
    warm(false);
  }

  @Override
  public void warm(boolean force) {
    if (mUMSConfiguration.isEnableCacheWarmer() && login()) {
      CacheManager cacheManager = mCacheFactory.getCacheManager();
      if (force || cacheManager.get(WARMER_KEY) == null
          || ((CacheWarmer) cacheManager.get(WARMER_KEY)).getState() == CacheWarmer.State.NONE) {

        mCacheWarmer = new CacheWarmer(CacheWarmer.State.IN_PROGRESS);

        cacheManager.put(WARMER_KEY, mCacheWarmer);

        try {
          if (mContentManagers.length > 0) {
            // start warming up
            mLogger.info("Started warming up cache");
            for (ContentManager contentManager : mContentManagers) {
              contentManager.getAll();
            }
          }

          mCacheWarmer = new CacheWarmer(CacheWarmer.State.WARMED);
          cacheManager.put(WARMER_KEY, mCacheWarmer);
          mLogger.info("Cache warm up finish");

        } catch (Exception e) {
          mCacheWarmer = new CacheWarmer(CacheWarmer.State.NONE);
          cacheManager.put(WARMER_KEY, mCacheWarmer);
          mLogger.error("Failed to warm up cache properly, will now fallback to initial state", e);
        }
      }
    }
  }

  protected boolean login() {
    SecurityUtils.setSecurityManager(mSecurityManager);
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token =
        new UsernamePasswordToken(mUMSConfiguration.getBackendUser(), mUMSConfiguration.getBackendUserPassword());

    try {
      // Authenticate the subject
      subject.login(token);
      return true;
    } catch (Exception e) {
      mLogger.error("Exception while login using back end user ", e);
    }
    return false;
  }

  public CacheWarmerManagerImpl(SecurityManager pSecurityManager, CacheFactory pCacheFactory,
                                UMSConfiguration pUMSConfiguration, KafkaProducerConfig pKafkaProducerConfig, ContentManager... pContentManagers) {
    mSecurityManager = pSecurityManager;
    mContentManagers = pContentManagers;
    mCacheFactory = pCacheFactory;
    mUMSConfiguration = pUMSConfiguration;
  }
}
