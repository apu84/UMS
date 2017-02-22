package org.ums.domain.model.common;

@Deprecated
public interface Mutable {
  void commit(final boolean update);

  void delete();
}
