package org.ums.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.manager.ContentManager;

import java.util.ArrayList;
import java.util.List;

public class ContentDaoDecorator<R, M, I, C extends ContentManager<R, M, I>> implements
    ContentManager<R, M, I> {
  private C mManager;
  private static final Logger mLogger = LoggerFactory.getLogger(ContentDaoDecorator.class);

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
  public List<R> getAll() {
    return getManager().getAll();
  }

  @Override
  public R get(final I pId) {
    return getManager().get(pId);
  }

  @Override
  public R validate(R pReadonly) {
    return getManager() == null ? pReadonly : getManager().validate(pReadonly);
  }

  @Override
  public int update(final M pMutable) {
    int updated = getManager().update(pMutable);
    if(updated <= 0) {
      throw new IllegalArgumentException("No entry has been updated");
    }
    return updated;
  }

  @Override
  public int update(final List<M> pMutableList) {
    int updated = getManager().update(pMutableList);
    if(updated <= 0) {
      throw new IllegalArgumentException("No entry has been updated");
    }
    return updated;
  }

  @Override
  public int delete(final M pMutable) {
    int deleted = getManager().delete(pMutable);
    if(deleted <= 0) {
      throw new IllegalArgumentException("No entry has been deleted");
    }
    return deleted;
  }

  @Override
  public int delete(final List<M> pMutableList) {
    int deleted = getManager().delete(pMutableList);
    if(deleted <= 0) {
      throw new IllegalArgumentException("No entry has been deleted");
    }
    return deleted;
  }

  @Override
  public I create(final M pMutable) {
    return getManager().create(pMutable);
  }

  @Override
  public List<I> create(final List<M> pMutableList) {
    return getManager().create(pMutableList);
  }

  @Override
  public boolean exists(I pId) {
    return getManager().exists(pId);
  }

  protected List<I> getIdList(final List<Object[]> paramsList) {
    List<I> ids = new ArrayList<I>();
    for(Object[] params : paramsList) {
      ids.add((I) params[0]);
    }
    return ids;
  }
}
