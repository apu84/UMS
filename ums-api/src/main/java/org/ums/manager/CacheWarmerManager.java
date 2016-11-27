package org.ums.manager;

public interface CacheWarmerManager {
  String WARMER_KEY = "WARM_UP";

  void warm() throws Exception;

  void warm(boolean force) throws Exception;
}
