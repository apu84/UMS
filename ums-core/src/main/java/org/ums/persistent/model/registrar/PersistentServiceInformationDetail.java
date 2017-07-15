package org.ums.persistent.model.registrar;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.registrar.MutableServiceInformationDetail;
import org.ums.enums.common.EmploymentPeriod;
import org.ums.manager.registrar.ServiceInformationDetailManager;

import java.util.Date;

public class PersistentServiceInformationDetail implements MutableServiceInformationDetail {

  private static ServiceInformationDetailManager sServiceInformationDetailManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sServiceInformationDetailManager =
        applicationContext.getBean("serviceInformationDetailManager", ServiceInformationDetailManager.class);
  }

  private int mId;
  private EmploymentPeriod mEmploymentPeriod;
  private int mEmploymentPeriodId;
  private Date mStartDate;
  private Date mEndDate;
  private int mServiceId;
  private String mLastModified;

  public PersistentServiceInformationDetail() {}

  public PersistentServiceInformationDetail(PersistentServiceInformationDetail pPersistentServiceInformationDetail) {
    mId = pPersistentServiceInformationDetail.getId();
    mEmploymentPeriod = pPersistentServiceInformationDetail.getEmploymentPeriod();
    mEmploymentPeriodId = pPersistentServiceInformationDetail.getEmploymentPeriodId();
    mStartDate = pPersistentServiceInformationDetail.getStartDate();
    mEndDate = pPersistentServiceInformationDetail.getEndDate();
    mServiceId = pPersistentServiceInformationDetail.getServiceId();
    mLastModified = pPersistentServiceInformationDetail.getLastModified();
  }

  @Override
  public MutableServiceInformationDetail edit() {
    return new PersistentServiceInformationDetail(this);
  }

  @Override
  public Integer create() {
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
  public void setServiceId(int pServiceId) {
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
  public int getServiceId() {
    return mServiceId;
  }
}
