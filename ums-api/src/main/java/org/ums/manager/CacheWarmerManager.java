package org.ums.manager;

public interface CacheWarmerManager {
  String WARMER_KEY = "WARM_UP";

  void warm();

  void warm(boolean force);
}
