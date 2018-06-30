package org.ums.microservice.instance.cachewarmer;

import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.configuration.UMSConfiguration;
import org.ums.manager.CacheWarmerManager;
import org.ums.manager.ContentManager;
import org.ums.microservice.AbstractService;
import org.ums.microservice.configuration.ServiceConfiguration;

public class CacheWarmerManagerImpl extends AbstractService implements CacheWarmerManager {
  private static final Logger mLogger = LoggerFactory.getLogger(CacheWarmerManagerImpl.class);

  UMSConfiguration mUMSConfiguration;

  ServiceConfiguration mServiceConfiguration;

  SecurityManager mSecurityManager;

  private ContentManager[] mContentManagers;

  @Override
  public void warm() {
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

  public CacheWarmerManagerImpl(SecurityManager pSecurityManager, UMSConfiguration pUMSConfiguration,
      ServiceConfiguration pServiceConfiguration, ContentManager... pContentManagers) {
    mSecurityManager = pSecurityManager;
    mContentManagers = pContentManagers;
    mUMSConfiguration = pUMSConfiguration;
    mServiceConfiguration = pServiceConfiguration;
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
