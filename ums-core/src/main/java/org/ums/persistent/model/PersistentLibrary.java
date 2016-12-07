package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableLibrary;
import org.ums.manager.LibraryManager;

/**
 * Created by kawsu on 12/4/2016.
 */
public class PersistentLibrary implements MutableLibrary {

  private static LibraryManager sLibraryManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sLibraryManager = applicationContext.getBean("libraryManager", LibraryManager.class);
  }

  private int mId;
  private String mBookName;
  private String mAuthorName;
  private String mLastModified;

  public PersistentLibrary() {

  }

  public PersistentLibrary(final MutableLibrary pMutableLibrary) {
    setId(pMutableLibrary.getId());
    setBookName(pMutableLibrary.getBookName());
    setAuthorName(pMutableLibrary.getAuthorName());
    setLastModified(pMutableLibrary.getLastModified());
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sLibraryManager.update(this);
    }
    else {
      sLibraryManager.create(this);
    }
  }

  @Override
  public MutableLibrary edit() {
    return new PersistentLibrary(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified == null ? "" : mLastModified;
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void delete() {
    sLibraryManager.delete(this);

  }

  @Override
  public void setBookName(String pBookName) {
    mBookName = pBookName;

  }

  @Override
  public void setAuthorName(String pAuthorName) {

    mAuthorName = pAuthorName;
  }

  @Override
  public String getBookName() {
    return mBookName;
  }

  @Override
  public String getAuthorName() {
    return mAuthorName;
  }
}
