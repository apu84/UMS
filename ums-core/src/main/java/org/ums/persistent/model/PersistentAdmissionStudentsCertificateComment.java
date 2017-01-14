package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateComment;
import org.ums.manager.AdmissionStudentsCertificateCommentManager;

/**
 * Created by kawsu on 1/12/2017.
 */
public class PersistentAdmissionStudentsCertificateComment implements
    MutableAdmissionStudentsCertificateComment {

  private static AdmissionStudentsCertificateCommentManager sAdmissionStudentsCertificateCommentManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    applicationContext.getBean("admissionStudentsCertificateCommentManager",
        AdmissionStudentsCertificateCommentManager.class);
  }

  private int mRowId;
  private int mSemesterId;
  private String mReceiptId;
  private String mComment;
  private String mLastModified;

  public PersistentAdmissionStudentsCertificateComment() {

  }

  public PersistentAdmissionStudentsCertificateComment(
      PersistentAdmissionStudentsCertificateComment pAdmissionStudentsCertificateComment) {

    mRowId = pAdmissionStudentsCertificateComment.getRowId();
    mSemesterId = pAdmissionStudentsCertificateComment.getSemesterId();
    mReceiptId = pAdmissionStudentsCertificateComment.getReceiptId();
    mComment = pAdmissionStudentsCertificateComment.getComment();
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sAdmissionStudentsCertificateCommentManager.update(this);
    }
    else {
      sAdmissionStudentsCertificateCommentManager.create(this);
    }
  }

  @Override
  public void delete() {
    sAdmissionStudentsCertificateCommentManager.delete(this);
  }

  @Override
  public MutableAdmissionStudentsCertificateComment edit() {
    return new PersistentAdmissionStudentsCertificateComment(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Integer getId() {
    return mRowId;
  }

  @Override
  public void setId(Integer pId) {
    mRowId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public void setReceiptId(String pReceiptId) {
    mReceiptId = pReceiptId;
  }

  @Override
  public void setComment(String pComment) {
    mComment = pComment;
  }

  @Override
  public int getRowId() {
    return mRowId;
  }

  @Override
  public int getSemesterId() {
    return mSemesterId;
  }

  @Override
  public String getReceiptId() {
    return mReceiptId;
  }

  @Override
  public String getComment() {
    return mComment;
  }
}
