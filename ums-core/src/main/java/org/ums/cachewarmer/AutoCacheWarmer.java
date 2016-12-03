package org.ums.cachewarmer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.manager.CacheWarmerManager;

public class AutoCacheWarmer {
  private static final Logger mLogger = LoggerFactory.getLogger(AutoCacheWarmer.class);

  public AutoCacheWarmer(final CacheWarmerManager pCacheWarmerManager) {
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    scheduler.schedule(() -> {
      try {
        pCacheWarmerManager.warm();
      } catch(Exception e) {
        mLogger.error("Failed to warm cache ", e);
      }
    }, 60, TimeUnit.SECONDS);
  }
}
