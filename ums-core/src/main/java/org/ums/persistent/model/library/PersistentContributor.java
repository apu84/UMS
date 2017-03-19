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
    mFirstName = pPersistentContributor.getFirstName();
    mMiddleName = pPersistentContributor.getMiddleName();
    mLastName = pPersistentContributor.getLastName();
    mShortName = pPersistentContributor.getShortName();
    mGender = pPersistentContributor.getGender();
    mAddress = pPersistentContributor.getAddress();
    mCountry = pPersistentContributor.getCountry();
    mContributorCategory = pPersistentContributor.getCategory();
    mRefUserId = pPersistentContributor.getRefUserId();
  }

  private Integer mId;
  private String mMfn;
  private String mFirstName;
  private String mMiddleName;
  private String mLastName;
  private String mShortName;
  private Gender mGender;
  private String mAddress;
  private Country mCountry;
  private ContributorCategory mContributorCategory;
  private String mRefUserId;
  private String mLastModified;

  @Override
  public void commit(boolean update) {
    if(update) {
      sContributorManager.update(this);
    }
    else {
      sContributorManager.create(this);
    }
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
  public void delete() {
    sContributorManager.delete(this);
  }

  @Override
  public void setFirstName(String pFirstName) {
    mFirstName = pFirstName;
  }

  @Override
  public String getFirstName() {
    return mFirstName;
  }

  @Override
  public void setMiddleName(String pMiddleName) {
    mMiddleName = pMiddleName;
  }

  @Override
  public String getMiddleName() {
    return mMiddleName;
  }

  @Override
  public String getLastName() {
    return mLastName;
  }

  @Override
  public void setLastName(String pLastName) {
    mLastModified = pLastName;
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
}
