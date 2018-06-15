package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.MutableCompany;
import org.ums.domain.model.mutable.MutableUserCompanyMap;
import org.ums.manager.CompanyManager;
import org.ums.manager.UserCompanyMapManager;

import java.util.List;

public class PersistentUserCompanyMap implements MutableUserCompanyMap {

  private static UserCompanyMapManager sUserCompanyMapManager;
  private Long mId;
  private String mUserId;
  private String mCompanyId;
  private List<Company> mCompanyList;
  private String mLastModified;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  public String getUserId() {
    return mUserId;
  }

  @Override
  public void setUserId(String pUserId) {
    this.mUserId = pUserId;
  }

  @Override
  public String getCompanyId() {
    return mCompanyId;
  }

  @Override
  public void setCompanyId(String pCompanyId) {
    this.mCompanyId = pCompanyId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.mLastModified = pLastModified;
  }

  @Override
  public String create() {
    return null;
  }

  @Override
  public void update() {
    sUserCompanyMapManager.update(this);
  }

  @Override
  public MutableUserCompanyMap edit() {
    return new PersistentUserCompanyMap(this);
  }

  @Override
  public void delete() {
    sUserCompanyMapManager.delete(this);
  }

  public PersistentUserCompanyMap() {}

  public PersistentUserCompanyMap(MutableUserCompanyMap pUserCompanyMap) {
    setId(pUserCompanyMap.getId());
    setUserId(pUserCompanyMap.getUserId());
    setCompanyId(pUserCompanyMap.getCompanyId());
    setLastModified(pUserCompanyMap.getLastModified());
  }

  public PersistentUserCompanyMap(Long pId, String pUserId, String pCompanyId) {
    mId = pId;
    mUserId = pUserId;
    mCompanyId = pCompanyId;
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sUserCompanyMapManager = applicationContext.getBean("userCompanyMapManager", UserCompanyMapManager.class);
  }
}
