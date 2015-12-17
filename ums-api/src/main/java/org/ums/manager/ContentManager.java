package org.ums.manager;


import java.util.List;

public interface ContentManager<R, M, I> {
  List<R> getAll() throws Exception;

  R get(final I pId) throws Exception;

  void update(final M pMutable) throws Exception;

  void delete(final M pMutable) throws Exception;

  void create(final M pMutable) throws Exception;
}
