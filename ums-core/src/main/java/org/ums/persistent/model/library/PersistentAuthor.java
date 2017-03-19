package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.library.AuthorManager;

/**
 * Created by Ifti on 30-Jan-17.
 */

public class PersistentAuthor implements MutableAuthor {

  private static AuthorManager sAuthorManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sAuthorManager = applicationContext.getBean("authorManager", AuthorManager.class);
  }

  private Integer mId;
  private String mFirstName;
  private String mMiddleName;
  private String mLastName;
  private String mShortName;
  private String mGender;
  private String mAddress;
  private int mCountryId;
  private Country mCountry;
  private String mLastModified;

  public PersistentAuthor() {}

  public PersistentAuthor(final PersistentAuthor pPersistentAuthor) {
    mId = pPersistentAuthor.getId();
    mFirstName = pPersistentAuthor.getFirstName();
    mMiddleName = pPersistentAuthor.getMiddleName();
    mLastName = pPersistentAuthor.getLastName();
    mShortName = pPersistentAuthor.getShortName();
    mGender = pPersistentAuthor.getGender();
    mAddress = pPersistentAuthor.getAddress();
    mCountryId = pPersistentAuthor.getCountryId();
  }

  @Override
  public String getLastModified() {
    return null;
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
    mLastName = pLastName;
  }

  @Override
  public String getShortName() {
    return mShortName;
  }

  @Override
  public String getGender() {
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
  public int getCountryId() {
    return mCountryId;
  }

  @Override
  public void setGender(String pGender) {
    mGender = pGender;
  }

  @Override
  public Country getCountry() {
    return mCountry;
  }

  @Override
  public void setAddress(String pAddress) {
    mAddress = pAddress;
  }

  @Override
  public void setCountryId(int pCountryId) {
    mCountryId = pCountryId;
  }

  @Override
  public void setCountry(Country pCountry) {
    mCountry = pCountry;
  }

  @Override
  public Integer create() {
    return sAuthorManager.create(this);
  }

  @Override
  public void update() {
    sAuthorManager.update(this);
  }

  @Override
  public MutableAuthor edit() {
    return new PersistentAuthor(this);
  }

  @Override
  public void delete() {

    sAuthorManager.delete(this);
  }
}
