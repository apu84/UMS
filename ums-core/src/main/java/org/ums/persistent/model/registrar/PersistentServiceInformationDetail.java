package org.ums.persistent.model.registrar;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.registrar.MutableServiceInformationDetail;
import org.ums.manager.registrar.ServiceInformationDetailManager;

public class PersistentServiceInformationDetail implements MutableServiceInformationDetail {

  private static ServiceInformationDetailManager sServiceInformationDetailManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sServiceInformationDetailManager =
        applicationContext.getBean("serviceInformationDetailManager", ServiceInformationDetailManager.class);
  }

  private int mId;
  private String mLastModified;

  public PersistentServiceInformationDetail() {}

  public PersistentServiceInformationDetail(PersistentServiceInformationDetail pPersistentServiceInformationDetail) {
    mId = pPersistentServiceInformationDetail.getId();
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
}
