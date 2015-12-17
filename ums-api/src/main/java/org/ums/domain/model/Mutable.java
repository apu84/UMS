package org.ums.domain.model;


public interface Mutable {
  void commit(final boolean update) throws Exception;

  void delete() throws Exception;
}
