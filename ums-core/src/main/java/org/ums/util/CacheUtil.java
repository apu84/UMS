package org.ums.util;

import java.util.ArrayList;
import java.util.List;

public class CacheUtil {
  public static String getCacheKey(Class pClass, Object pId) {
    return pClass.getName() + "-" + pId;
  }

  public static <T>void addReferrer(List<T> keyList,T pReferrer){
    if(keyList == null) {
      keyList = new ArrayList<>();
      keyList.add(pReferrer);
    }
    else {
      if(!keyList.contains(pReferrer)) {
        keyList.add(pReferrer);
      }
    }
  }

}
