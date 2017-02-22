package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.library.MutableSubject;
import org.ums.manager.library.NoteManager;
import org.ums.manager.library.SubjectManager;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class PersistentSubject implements MutableSubject {
  private static SubjectManager mSubjectManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    mSubjectManager = applicationContext.getBean("subjectManager", SubjectManager.class);
  }

  private Integer mId;
  private String mMfn;
  private String mSubject;
  private Integer mViewOrder;
  private String mLastModified;

  public PersistentSubject() {}

  public PersistentSubject(final PersistentSubject pPersistentSubject) {
    mId = pPersistentSubject.getId();
    mMfn = pPersistentSubject.getMfn();
    mSubject = pPersistentSubject.getSubject();
    mViewOrder = pPersistentSubject.getViewOrder();
    mLastModified = pPersistentSubject.getLastModified();
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      mSubjectManager.update(this);
    }
    else {
      mSubjectManager.create(this);
    }
  }

  @Override
  public MutableSubject edit() {
    return new PersistentSubject(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
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
    mSubjectManager.delete(this);
  }

  @Override
  public String getMfn() {
    return mMfn;
  }

  @Override
  public Integer getViewOrder() {
    return mViewOrder;
  }

  @Override
  public void setMfn(String pMfn) {
    mMfn = pMfn;
  }

  @Override
  public String getSubject() {
    return mSubject;
  }

  @Override
  public void setViewOrder(Integer pViewOrder) {
    mViewOrder = pViewOrder;
  }

  @Override
  public void getSubject(String pSubject) {
    mSubject = pSubject;
  }

}
