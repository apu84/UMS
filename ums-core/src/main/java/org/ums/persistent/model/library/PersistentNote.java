package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.library.MutableNote;
import org.ums.manager.library.NoteManager;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class PersistentNote implements MutableNote {
  private static NoteManager sNoteManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sNoteManager = applicationContext.getBean("noteManager", NoteManager.class);
  }

  private Integer mId;
  private String mMfn;
  private String mNote;
  private Integer mViewOrder;
  private String mLastModified;

  public PersistentNote() {}

  public PersistentNote(final PersistentNote pPersistentNote) {
    mId = pPersistentNote.getId();
    mMfn = pPersistentNote.getMfn();
    mNote = pPersistentNote.getNote();
    mViewOrder = pPersistentNote.getViewOrder();
    mLastModified = pPersistentNote.getLastModified();
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sNoteManager.update(this);
    }
    else {
      sNoteManager.create(this);
    }
  }

  @Override
  public MutableNote edit() {
    return new PersistentNote(this);
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
    sNoteManager.delete(this);
  }

  @Override
  public void setMfn(String pMfn) {
    mMfn = pMfn;
  }

  @Override
  public void setViewOrder(Integer pViewOrder) {
    mViewOrder = pViewOrder;
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
  public void setNote(String pNote) {
    mNote = pNote;
  }

  @Override
  public String getNote() {
    return mNote;
  }
}
