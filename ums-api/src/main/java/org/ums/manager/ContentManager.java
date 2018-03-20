package org.ums.manager;

import java.util.List;

public interface ContentManager<R, M, I> {
  List<R> getAll();

  R get(final I pId);

  R validate(final R pReadonly);

  int update(final M pMutable);

  int update(final List<M> pMutableList);

  int delete(final M pMutable);

  int delete(final List<M> pMutableList);

  I create(final M pMutable);

  List<I> create(final List<M> pMutableList);

  boolean exists(final I pId);

  int count(final List<M> pMutableList);

  void invalidateCache(M pMutable);
}
