package org.ums.academic.dao;


import org.ums.manager.ContentManager;

import java.util.List;

public class ContentDaoDecorator<R, M, I> implements ContentManager<R, M, I> {
  private ContentManager<R, M, I> mManager;

  public ContentManager<R, M, I> getManager() {
    return mManager;
  }

  public void setManager(ContentManager<R, M, I> pManager) {
    mManager = pManager;
  }

  public List<R> getAll() throws Exception {
    return getManager().getAll();
  }

  public R get(final I pId) throws Exception {
    return getManager().get(pId);
  }

  public void update(final M pMutable) throws Exception {
    getManager().update(pMutable);
  }

  public void delete(final M pMutable) throws Exception {
    getManager().delete(pMutable);
  }

  public void create(final M pMutable) throws Exception {
    getManager().create(pMutable);
  }

  protected static String getLastModifiedSql() {
    return "to_char(sysdate,'YYYYMMDDHHMISS')";
  }
}
