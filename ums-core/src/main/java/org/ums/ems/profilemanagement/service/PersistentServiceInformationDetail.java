package org.ums.ems.profilemanagement.service;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.enums.common.EmploymentPeriod;

import java.util.Date;

public class PersistentServiceInformationDetail implements MutableServiceInformationDetail {

  private static ServiceInformationDetailManager sServiceInformationDetailManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sServiceInformationDetailManager =
        applicationContext.getBean("serviceInformationDetailManager", ServiceInformationDetailManager.class);
  }

  private Long mId;
  private EmploymentPeriod mEmploymentPeriod;
  private int mEmploymentPeriodId;
  private Date mStartDate;
  private Date mEndDate;
  private String mComment;
  private Long mServiceId;
  private String mLastModified;

  public PersistentServiceInformationDetail() {}

  public PersistentServiceInformationDetail(PersistentServiceInformationDetail pPersistentServiceInformationDetail) {
    mId = pPersistentServiceInformationDetail.getId();
    mEmploymentPeriod = pPersistentServiceInformationDetail.getEmploymentPeriod();
    mEmploymentPeriodId = pPersistentServiceInformationDetail.getEmploymentPeriodId();
    mStartDate = pPersistentServiceInformationDetail.getStartDate();
    mEndDate = pPersistentServiceInformationDetail.getEndDate();
    mComment = pPersistentServiceInformationDetail.getComment();
    mServiceId = pPersistentServiceInformationDetail.getServiceId();
    mLastModified = pPersistentServiceInformationDetail.getLastModified();
  }

  @Override
  public MutableServiceInformationDetail edit() {
    return new PersistentServiceInformationDetail(this);
  }

  @Override
  public Long create() {
    return sServiceInformationDetailManager.create(this);
  }

  @Override
  public void update() {
    sServiceInformationDetailManager.update(this);
  }

  @Override
  public void delete() {
    sServiceInformationDetailManager.delete(this);
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public void setEmploymentPeriod(EmploymentPeriod pEmploymentPeriod) {
    mEmploymentPeriod = pEmploymentPeriod;
  }

  @Override
  public void setEmploymentPeriodId(int pEmploymentPeriodId) {
    mEmploymentPeriodId = pEmploymentPeriodId;
  }

  @Override
  public void setStartDate(Date pStartDate) {
    mStartDate = pStartDate;
  }

  @Override
  public void setEndDate(Date pEndDate) {
    mEndDate = pEndDate;
  }

  @Override
  public void setComment(String pComment) {
    mComment = pComment;
  }

  @Override
  public void setServiceId(Long pServiceId) {
    mServiceId = pServiceId;
  }

  @Override
  public EmploymentPeriod getEmploymentPeriod() {
    return mEmploymentPeriod;
  }

  @Override
  public int getEmploymentPeriodId() {
    return mEmploymentPeriodId;
  }

  @Override
  public Date getStartDate() {
    return mStartDate;
  }

  @Override
  public Date getEndDate() {
    return mEndDate;
  }

  @Override
  public String getComment() {
    return mComment;
  }

  @Override
  public Long getServiceId() {
    return mServiceId;
  }
}
