package org.ums.domain.model;

public interface Cacheable<I> extends Identifier<I> {
  default String getCacheKey() {
    return getClass().getName() + "-" + getId();
  }
}
