package org.ums.persistent.model.common;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.common.MutableCountry;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.manager.common.CountryManager;
import org.ums.persistent.model.library.PersistentAuthor;

/**
 * Created by Ifti on 31-Jan-17.
 */
public class PersistentCountry implements MutableCountry {

  private static CountryManager sCountryManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sCountryManager = applicationContext.getBean("countryManager", CountryManager.class);
  }

  private Integer mId;
  private String mCode;
  private String mName;
  private String mLastModified;

  public PersistentCountry() {}

  public PersistentCountry(final PersistentCountry ppPersistentCountry) {
    mId = ppPersistentCountry.getId();
    mCode = ppPersistentCountry.getCode();
    mName = ppPersistentCountry.getName();
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
  public void setCode(String pCode) {
    mCode = pCode;
  }

  @Override
  public String getCode() {
    return mCode;
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
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public Integer create() {
    return sCountryManager.create(this);
  }

  @Override
  public void update() {
    sCountryManager.update(this);
  }

  @Override
  public MutableCountry edit() {
    return new PersistentCountry(this);
  }

  @Override
  public void delete() {

    sCountryManager.delete(this);
  }
}
