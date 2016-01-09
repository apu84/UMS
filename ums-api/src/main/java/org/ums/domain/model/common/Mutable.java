package org.ums.domain.model.common;


public interface Mutable {
  void commit(final boolean update) throws Exception;

  void delete() throws Exception;
}
