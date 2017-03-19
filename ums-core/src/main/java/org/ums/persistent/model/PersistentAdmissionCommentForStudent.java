package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableAdmissionCommentForStudent;
import org.ums.manager.AdmissionCommentForStudentManager;

import java.util.Date;

public class PersistentAdmissionCommentForStudent implements MutableAdmissionCommentForStudent {

  private static AdmissionCommentForStudentManager sAdmissionCommentForStudentManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    applicationContext.getBean("admissionStudentsCertificateCommentManager", AdmissionCommentForStudentManager.class);
  }

  private int mRowId;
  private int mSemesterId;
  private String mReceiptId;
  private String mComment;
  private String mLastModified;
  private String mCommentedOn;

  public PersistentAdmissionCommentForStudent() {

  }

  public PersistentAdmissionCommentForStudent(PersistentAdmissionCommentForStudent pAdmissionStudentsCertificateComment) {

    mRowId = pAdmissionStudentsCertificateComment.getRowId();
    mSemesterId = pAdmissionStudentsCertificateComment.getSemesterId();
    mReceiptId = pAdmissionStudentsCertificateComment.getReceiptId();
    mComment = pAdmissionStudentsCertificateComment.getComment();
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sAdmissionCommentForStudentManager.update(this);
    }
    else {
      sAdmissionCommentForStudentManager.create(this);
    }
  }

  @Override
  public void delete() {
    sAdmissionCommentForStudentManager.delete(this);
  }

  @Override
  public MutableAdmissionCommentForStudent edit() {
    return new PersistentAdmissionCommentForStudent(this);
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
  public void setCommentedOn(String pCommentedOn) {
    mCommentedOn = pCommentedOn;
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

  @Override
  public String getCommentedOn() {
    return mCommentedOn;
  }
}
