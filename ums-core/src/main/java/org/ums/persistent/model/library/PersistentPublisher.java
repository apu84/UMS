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

  private Integer mId;
  private String mName;
  private int mCountryId;
  private Country mCountry;
  private String mLastModified;

  public PersistentPublisher() {}

  public PersistentPublisher(final PersistentPublisher pPersistentPublisher) {
    mId = pPersistentPublisher.getId();
    mName = pPersistentPublisher.getName();
    mCountryId = pPersistentPublisher.getCountryId();
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
  public void setName(String pName) {
    mName = pName;
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public void setCountryId(int pCountryId) {
    mCountryId = pCountryId;
  }

  @Override
  public int getCountryId() {
    return mCountryId;
  }

  @Override
  public Country getCountry() {
    return sCountryManager.get(mId);
  }

  @Override
  public void setCountry(Country pCountry) {
    mCountry = pCountry;
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sSupplierManager.update(this);
    }
    else {
      sSupplierManager.create(this);
    }
  }

  @Override
  public MutablePublisher edit() {
    return new PersistentPublisher(this);
  }

  @Override
  public void delete() {

    sSupplierManager.delete(this);
  }
}
