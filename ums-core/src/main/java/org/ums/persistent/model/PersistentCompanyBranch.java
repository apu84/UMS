package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.MutableCompanyBranch;
import org.ums.manager.CompanyBranchManager;
import org.ums.manager.CompanyManager;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */
public class PersistentCompanyBranch implements MutableCompanyBranch {

  private static CompanyManager sCompanyManager;
  private static CompanyBranchManager sCompanyBranchManager;
  private Long mId;
  private Company mCompany;
  private Long mCompanyId;
  private String mName;
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
  public Company getCompany() {
    return mCompany == null ? sCompanyManager.get(mCompanyId) : sCompanyManager.validate(mCompany);
  }

  @Override
  public void setCompany(Company pCompany) {
    this.mCompany = pCompany;
  }

  @Override
  public Long getCompanyId() {
    return mCompanyId;
  }

  @Override
  public void setCompanyId(Long pCompanyId) {
    this.mCompanyId = pCompanyId;
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
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.mLastModified = pLastModified;
  }

  @Override
  public Long create() {
    return sCompanyBranchManager.create(this);
  }

  @Override
  public void update() {
    sCompanyBranchManager.update(this);
  }

  @Override
  public MutableCompanyBranch edit() {
    return new PersistentCompanyBranch(this);
  }

  @Override
  public void delete() {
    sCompanyBranchManager.delete(this);
  }

  public PersistentCompanyBranch() {}

  public PersistentCompanyBranch(MutableCompanyBranch pCompanyBranch) {
    setId(pCompanyBranch.getId());
    setCompany(pCompanyBranch.getCompany());
    setCompanyId(pCompanyBranch.getCompanyId());
    setName(pCompanyBranch.getName());
    setLastModified(pCompanyBranch.getLastModified());
  }

  public PersistentCompanyBranch(Long pId, Long pCompanyId, String pName) {
    mId = pId;
    mCompanyId = pCompanyId;
    mName = pName;
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCompanyManager = applicationContext.getBean("companyManager", CompanyManager.class);
    sCompanyBranchManager = applicationContext.getBean("companyBranchManager", CompanyBranchManager.class);
  }
}
