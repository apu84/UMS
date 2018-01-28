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
  private Long mId;
  private String mName;
  private String mShortName;
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
  public Long create() {
    return sCompanyManager.create(this);
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

  public PersistentCompany(Long pId, String pName, String pShortName) {
    mId = pId;
    mName = pName;
    mShortName = pShortName;
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCompanyManager = applicationContext.getBean("companyManager", CompanyManager.class);
  }
}
