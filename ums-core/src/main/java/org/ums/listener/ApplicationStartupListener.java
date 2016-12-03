package org.ums.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.ums.manager.CacheWarmerManager;

@Component
public class ApplicationStartupListener {
  private static final Logger mLogger = LoggerFactory.getLogger(ApplicationStartupListener.class);

  @Autowired
  @Lazy
  CacheWarmerManager mCacheWarmerManager;

  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    scheduler.schedule(() -> {
      try {
        mCacheWarmerManager.warm();
      } catch(Exception e) {
        mLogger.error("Failed to warm cache ", e);
      }
    }, 120, TimeUnit.SECONDS);
  }
}
