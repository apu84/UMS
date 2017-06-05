package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.mutable.library.MutablePublisher;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.common.CountryManager;
import org.ums.manager.library.PublisherManager;
import org.ums.manager.library.SupplierManager;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class PersistentPublisher implements MutablePublisher {

  private static PublisherManager sSupplierManager;
  private static CountryManager sCountryManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSupplierManager = applicationContext.getBean("publisherManager", PublisherManager.class);
    sCountryManager = applicationContext.getBean("countryManager", CountryManager.class);
  }

  private Long mId;
  private String mName;
  private Integer mCountryId;
  private Country mCountry;
  private String mContactPerson;
  private String mPhoneNumber;
  private String mEmailAddress;
  private String mLastModified;

  public PersistentPublisher() {}

  public PersistentPublisher(final PersistentPublisher pPersistentPublisher) {
    mId = pPersistentPublisher.getId();
    mName = pPersistentPublisher.getName();
    mCountryId = pPersistentPublisher.getCountryId();
    mContactPerson = pPersistentPublisher.getContactPerson();
    mPhoneNumber = pPersistentPublisher.getPhoneNumber();
    mEmailAddress = pPersistentPublisher.getEmailAddress();
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
  public void setName(String pName) {
    mName = pName;
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public void setCountryId(Integer pCountryId) {
    mCountryId = pCountryId;
  }

  @Override
  public Integer getCountryId() {
    return mCountryId;
  }

  @Override
  public Country getCountry() {
    return sCountryManager.get(1);
  }

  @Override
  public void setCountry(Country pCountry) {
    mCountry = pCountry;
  }

  @Override
  public Long create() {
    return sSupplierManager.create(this);
  }

  @Override
  public void update() {
    sSupplierManager.update(this);
  }

  @Override
  public MutablePublisher edit() {
    return new PersistentPublisher(this);
  }

  @Override
  public void delete() {
    sSupplierManager.delete(this);
  }

  @Override
  public void setContactPerson(String pContactPerson) {
    mContactPerson = pContactPerson;
  }

  @Override
  public String getContactPerson() {
    return mContactPerson;
  }

  @Override
  public String getPhoneNumber() {
    return mPhoneNumber;
  }

  @Override
  public void setPhoneNumber(String pPhoneNumber) {
    mPhoneNumber = pPhoneNumber;
  }

  @Override
  public String getEmailAddress() {
    return mEmailAddress;
  }

  @Override
  public void setEmailAddress(String pEmailAddress) {
    mEmailAddress = pEmailAddress;
  }
}
