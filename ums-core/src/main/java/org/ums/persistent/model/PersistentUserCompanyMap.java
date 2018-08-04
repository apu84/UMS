package org.ums.persistent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.MutableUserCompanyMap;
import org.ums.manager.CompanyManager;
import org.ums.manager.UserCompanyMapManager;
import org.ums.serializer.UmsDateSerializer;

import java.util.Date;
import java.util.List;

public class PersistentUserCompanyMap implements MutableUserCompanyMap {

  private static UserCompanyMapManager sUserCompanyMapManager;
  private static CompanyManager sCompanyManager;

  private Long mId;
  private String mUserId;
  private String mCompanyId;
  private Company mCompany;
  private String mModifiedBy;
  private Date mModifiedDate;
  @JsonIgnore
  private List<Company> mCompanyList;
  private String mLastModified;

  @Override
  @JsonValue
  public Company getCompany() {
    return mCompany == null ? sCompanyManager.get(mCompanyId) : mCompany;
  }

  @Override
  @JsonIgnore
  public void setCompany(Company pCompany) {
    mCompany = pCompany;
  }

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
  public String getModifiedBy() {
    return mModifiedBy;
  }

  @Override
  public void setModifiedBy(String pModifiedBy) {
    mModifiedBy = pModifiedBy;
  }

  @Override
  @JsonSerialize(using = UmsDateSerializer.class)
  public Date getModifiedDate() {
    return mModifiedDate;
  }

  @Override
  @JsonIgnore
  public void setModifiedDate(Date pModifiedDate) {
    mModifiedDate = pModifiedDate;
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
    sCompanyManager = applicationContext.getBean("companyManager", CompanyManager.class);
    sUserCompanyMapManager = applicationContext.getBean("userCompanyMapManager", UserCompanyMapManager.class);
  }
}
