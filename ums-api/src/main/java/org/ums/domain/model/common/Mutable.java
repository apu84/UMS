package org.ums.domain.model.common;

public interface Mutable {
  void commit(final boolean update);

  void delete();
}
