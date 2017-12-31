package org.ums.mapper;

public interface Mapper<K, V> {
  void save(K pKey, V pValue);

  V lookup(K pKey);

  boolean contains(K pKey);
}
