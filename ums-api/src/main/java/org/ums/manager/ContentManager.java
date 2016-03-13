package org.ums.manager;


import java.util.List;

public interface ContentManager<R, M, I> {
  List<R> getAll() throws Exception;

  R get(final I pId) throws Exception;

  R validate(final R pReadonly) throws Exception;

  int update(final M pMutable) throws Exception;

  int update(final List<M> pMutableList) throws Exception;

  int delete(final M pMutable) throws Exception;

  int delete(final List<M> pMutableList) throws Exception;

  int create(final M pMutable) throws Exception;

  int create(final List<M> pMutableList) throws Exception;
}
