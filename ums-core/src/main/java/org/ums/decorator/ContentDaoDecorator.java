package org.ums.decorator;


import org.ums.manager.ContentManager;

import java.util.List;

public class ContentDaoDecorator<R, M, I, C extends ContentManager<R, M, I>> implements ContentManager<R, M, I> {
  private C mManager;

  protected static String getLastModifiedSql() {
    return "to_char(sysdate,'YYYYMMDDHHMISS')";
  }

  public C getManager() {
    return mManager;
  }

  public void setManager(C pManager) {
    mManager = pManager;
  }

  @Override
  public List<R> getAll() throws Exception {
    return getManager().getAll();
  }

  @Override
  public R get(final I pId) throws Exception {
    return getManager().get(pId);
  }

  @Override
  public R validate(R pReadonly) throws Exception {
    return getManager() == null ? pReadonly : getManager().validate(pReadonly);
  }

  @Override
  public int update(final M pMutable) throws Exception {
    int updated = getManager().update(pMutable);
    if (updated <= 0) {
      throw new IllegalArgumentException("No entry has been updated");
    }
    return updated;
  }

  @Override
  public int update(final List<M> pMutableList) throws Exception {
    int updated = getManager().update(pMutableList);
    if (updated <= 0) {
      throw new IllegalArgumentException("No entry has been updated");
    }
    return updated;
  }

  @Override
  public int delete(final M pMutable) throws Exception {
    int deleted = getManager().delete(pMutable);
    if (deleted <= 0) {
      throw new IllegalArgumentException("No entry has been deleted");
    }
    return deleted;
  }

  @Override
  public int delete(final List<M> pMutableList) throws Exception {
    int deleted = getManager().delete(pMutableList);
    if (deleted <= 0) {
      throw new IllegalArgumentException("No entry has been deleted");
    }
    return deleted;
  }

  @Override
  public int create(final M pMutable) throws Exception {
    int created = getManager().create(pMutable);
    if (created <= 0) {
      throw new IllegalArgumentException("No entry has been created");
    }
    return created;
  }

  @Override
  public int create(final List<M> pMutableList) throws Exception {
    int created = getManager().create(pMutableList);
    if (created <= 0) {
      throw new IllegalArgumentException("No entry has been created");
    }
    return created;
  }

  @Override
  public boolean exists(I pId) throws Exception {
    return getManager().exists(pId);
  }
}
