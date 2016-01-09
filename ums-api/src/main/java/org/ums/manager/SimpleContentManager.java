package org.ums.manager;

import java.util.List;

/**
 * Created by Ifti on 08-Jan-16.
 */
public interface SimpleContentManager <C,I> {
  List<C> getAll() throws Exception;

  C get(final I pId) throws Exception;

  void update(final C pSimpleObject) throws Exception;

  void delete(final C pSimpleObject) throws Exception;

  void create(final C pSimpleObject) throws Exception;
}
