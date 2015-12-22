package org.ums.util;


public class CacheUtil {
  public static String getCacheKey(Class pClass, Object pId) {
    return pClass.getName() + "-" + pId;
  }
}
