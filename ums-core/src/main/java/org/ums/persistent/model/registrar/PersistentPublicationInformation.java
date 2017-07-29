package org.ums.persistent.model.registrar;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;
import org.ums.enums.common.PublicationType;
import org.ums.manager.registrar.PublicationInformationManager;

import java.util.Date;

public class PersistentPublicationInformation implements MutablePublicationInformation {

  private static PublicationInformationManager sPublicationInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sPublicationInformationManager =
        applicationContext.getBean("publicationInformationManager", PublicationInformationManager.class);
  }

  private int mId;
  private String mEmployeeId;
  private String mTitle;
  private String mInterestGenre;
  private String mPublisherName;
  private Date mDateOfPublication;
  private PublicationType mType;
  private int mTypeId;
  private String mWebLink;
  private String mISSN;
  private String mIssue;
  private String mVolume;
  private String mJournalName;
  private Country mCountry;
  private int mCountryId;
  private String mPages;
  private String mStatus;
  private Date mAppliedOn;
  private Date mActionTakenOn;
  private int mRowNumber;
  private String mLastModified;

  public PersistentPublicationInformation() {}

  public PersistentPublicationInformation(PersistentPublicationInformation pPersistentPublicationInformation) {
    mId = pPersistentPublicationInformation.getId();
    mEmployeeId = pPersistentPublicationInformation.getEmployeeId();
    mTitle = pPersistentPublicationInformation.getTitle();
    mInterestGenre = pPersistentPublicationInformation.getInterestGenre();
    mPublisherName = pPersistentPublicationInformation.getPublisherName();
    mDateOfPublication = pPersistentPublicationInformation.getDateOfPublication();
    mType = pPersistentPublicationInformation.getType();
    mTypeId = pPersistentPublicationInformation.getTypeId();
    mWebLink = pPersistentPublicationInformation.getWebLink();
    mISSN = pPersistentPublicationInformation.getISSN();
    mIssue = pPersistentPublicationInformation.getIssue();
    mVolume = pPersistentPublicationInformation.getVolume();
    mJournalName = pPersistentPublicationInformation.getJournalName();
    mCountry = pPersistentPublicationInformation.getCountry();
    mCountryId = pPersistentPublicationInformation.getCountryId();
    mStatus = pPersistentPublicationInformation.getStatus();
    mPages = pPersistentPublicationInformation.getPages();
    mAppliedOn = pPersistentPublicationInformation.getAppliedOn();
    mActionTakenOn = pPersistentPublicationInformation.getActionTakenOn();
    mRowNumber = pPersistentPublicationInformation.getRowNumber();
  }

  @Override
  public MutablePublicationInformation edit() {
    return new PersistentPublicationInformation(this);
  }

  @Override
  public Integer create() {
    return sPublicationInformationManager.create(this);
  }

  @Override
  public void update() {
    sPublicationInformationManager.update(this);
  }

  @Override
  public void delete() {
    sPublicationInformationManager.delete(this);
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
  public void setEmployeeId(String pEmployeeId) {
    mEmployeeId = pEmployeeId;
  }

  @Override
  public void setTitle(String pTitle) {
    mTitle = pTitle;
  }

  @Override
  public void setInterestGenre(String pInterestGenre) {
    mInterestGenre = pInterestGenre;
  }

  @Override
  public void setPublisherName(String pPublisherName) {
    mPublisherName = pPublisherName;
  }

  @Override
  public void setDateOfPublication(Date pDateOfPublication) {
    mDateOfPublication = pDateOfPublication;
  }

  @Override
  public void setType(PublicationType pType) {
    mType = pType;
  }

  @Override
  public void setTypeId(int pTypeId) {
    mTypeId = pTypeId;
  }

  @Override
  public void setWebLink(String pWebLink) {
    mWebLink = pWebLink;
  }

  @Override
  public void setISSN(String pISSN) {
    mIssue = pISSN;
  }

  @Override
  public void setIssue(String pIssue) {
    mIssue = pIssue;
  }

  @Override
  public void setVolume(String pVolume) {
    mVolume = pVolume;
  }

  @Override
  public void setJournalName(String pJournalName) {
    mJournalName = pJournalName;
  }

  @Override
  public void setCountry(Country pCountry) {
    mCountry = pCountry;
  }

  @Override
  public void setCountryId(int pCountryId) {
    mCountryId = pCountryId;
  }

  @Override
  public void setPages(String pPages) {
    mPages = pPages;
  }

  @Override
  public void setStatus(String pStatus) {
    mStatus = pStatus;
  }

  @Override
  public void setAppliedOn(Date pAppliedOn) {
    mAppliedOn = pAppliedOn;
  }

  @Override
  public void setActionTakenOn(Date pActionTakenOn) {
    mActionTakenOn = pActionTakenOn;
  }

  @Override
  public void setRowNumber(int pRowNumber) {
    mRowNumber = pRowNumber;
  }

  @Override
  public String getEmployeeId() {
    return mEmployeeId;
  }

  @Override
  public String getTitle() {
    return mTitle;
  }

  @Override
  public String getInterestGenre() {
    return mInterestGenre;
  }

  @Override
  public String getPublisherName() {
    return mPublisherName;
  }

  @Override
  public Date getDateOfPublication() {
    return mDateOfPublication;
  }

  @Override
  public PublicationType getType() {
    return mType;
  }

  @Override
  public int getTypeId() {
    return mTypeId;
  }

  @Override
  public String getWebLink() {
    return mWebLink;
  }

  @Override
  public String getISSN() {
    return mISSN;
  }

  @Override
  public String getIssue() {
    return mIssue;
  }

  @Override
  public String getVolume() {
    return mVolume;
  }

  @Override
  public String getJournalName() {
    return mJournalName;
  }

  @Override
  public int getCountryId() {
    return mCountryId;
  }

  @Override
  public Country getCountry() {
    return mCountry;
  }

  @Override
  public String getPages() {
    return mPages;
  }

  @Override
  public String getStatus() {
    return mStatus;
  }

  @Override
  public Date getAppliedOn() {
    return mAppliedOn;
  }

  @Override
  public Date getActionTakenOn() {
    return mActionTakenOn;
  }

  @Override
  public int getRowNumber() {
    return mRowNumber;
  }

}
