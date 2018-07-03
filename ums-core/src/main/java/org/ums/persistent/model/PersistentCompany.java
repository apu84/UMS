package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableCompany;
import org.ums.manager.CompanyManager;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */

public class PersistentCompany implements MutableCompany {

  private static CompanyManager sCompanyManager;
  private String mId;
  private String mName;
  private String mShortName;
  private String mAddress;
  private String mLastModified;

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public String getAddress() {
    return mAddress;
  }

  @Override
  public void setAddress(String pAddress) {
    mAddress = pAddress;
  }

  @Override
  public void setId(String pId) {
    this.mId = pId;
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public void setName(String pName) {
    this.mName = pName;
  }

  @Override
  public String getShortName() {
    return mShortName;
  }

  @Override
  public void setShortName(String pShortName) {
    this.mShortName = pShortName;
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
    sCompanyManager.update(this);
  }

  @Override
  public MutableCompany edit() {
    return new PersistentCompany(this);
  }

  @Override
  public void delete() {
    sCompanyManager.delete(this);
  }

  public PersistentCompany() {}

  public PersistentCompany(MutableCompany pCompany) {
    setId(pCompany.getId());
    setName(pCompany.getName());
    setShortName(pCompany.getShortName());
    setLastModified(pCompany.getLastModified());
  }

  public PersistentCompany(String pId, String pName, String pShortName, String pAddress) {
    mId = pId;
    mName = pName;
    mShortName = pShortName;
    mAddress = pAddress;
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCompanyManager = applicationContext.getBean("companyManager", CompanyManager.class);
  }
}
