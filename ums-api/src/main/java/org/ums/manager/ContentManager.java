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

  int create(final M pMutable);

  int create(final List<M> pMutableList);

  boolean exists(final I pId);
}
