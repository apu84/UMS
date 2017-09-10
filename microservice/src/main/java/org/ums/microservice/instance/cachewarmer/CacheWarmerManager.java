package org.ums.microservice.instance.cachewarmer;

public interface CacheWarmerManager {
  String WARMER_KEY = "WARM_UP";

  void warm();

  void warm(boolean force);
}
