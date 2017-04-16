package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.mutable.library.MutableContributor;
import org.ums.enums.common.Gender;
import org.ums.enums.library.ContributorCategory;
import org.ums.manager.library.ContributorManager;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class PersistentContributor implements MutableContributor {
  private static ContributorManager sContributorManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sContributorManager = applicationContext.getBean("contributorManager", ContributorManager.class);
  }

  public PersistentContributor() {}

  public PersistentContributor(final PersistentContributor pPersistentContributor) {
    mId = pPersistentContributor.getId();
    mFullName = pPersistentContributor.getFullName();
    mShortName = pPersistentContributor.getShortName();
    mGender = pPersistentContributor.getGender();
    mAddress = pPersistentContributor.getAddress();
    mCountry = pPersistentContributor.getCountry();
    mCountryId = pPersistentContributor.getCountryId();
    mContributorCategory = pPersistentContributor.getCategory();
    mRefUserId = pPersistentContributor.getRefUserId();
  }

  private Long mId;
  private String mFullName;
  private String mShortName;
  private Gender mGender;
  private String mAddress;
  private Country mCountry;
  private Integer mCountryId;
  private ContributorCategory mContributorCategory;
  private String mRefUserId;
  private String mLastModified;

  @Override
  public Long create() {
    return sContributorManager.create(this);
  }

  @Override
  public void update() {
    sContributorManager.update(this);
  }

  @Override
  public MutableContributor edit() {
    return new PersistentContributor(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
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
  public void delete() {
    sContributorManager.delete(this);
  }

  @Override
  public void setFullName(String pFullName) {
    mFullName = pFullName;
  }

  @Override
  public String getFullName() {
    return mFullName;
  }

  @Override
  public String getShortName() {
    return mShortName;
  }

  @Override
  public Gender getGender() {
    return mGender;
  }

  @Override
  public void setShortName(String pShortName) {
    mShortName = pShortName;
  }

  @Override
  public String getAddress() {
    return mAddress;
  }

  @Override
  public Country getCountry() {
    return mCountry;
  }

  @Override
  public void setGender(Gender pGender) {
    mGender = pGender;
  }

  @Override
  public ContributorCategory getCategory() {
    return mContributorCategory;
  }

  @Override
  public void setAddress(String pAddress) {
    mAddress = pAddress;
  }

  @Override
  public String getRefUserId() {
    return mRefUserId;
  }

  @Override
  public void setCountry(Country pCountry) {
    mCountry = pCountry;
  }

  @Override
  public void setCategory(ContributorCategory pContributorCategory) {
    mContributorCategory = pContributorCategory;
  }

  @Override
  public void setRefUserId(String pRefUserId) {
    mRefUserId = pRefUserId;
  }

  public Integer getCountryId() {
    return mCountryId;
  }

  public void setCountryId(Integer mCountryId) {
    this.mCountryId = mCountryId;
  }
}
