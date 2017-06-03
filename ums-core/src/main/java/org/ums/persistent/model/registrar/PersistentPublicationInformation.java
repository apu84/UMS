package org.ums.persistent.model.registrar;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;
import org.ums.manager.registrar.PublicationInformationManager;

public class PersistentPublicationInformation implements MutablePublicationInformation {

  private static PublicationInformationManager sPublicationInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sPublicationInformationManager =
        applicationContext.getBean("publicationInformationManager", PublicationInformationManager.class);
  }

  private int mId;
  private String mEmployeeId;
  private String mPublicationTitle;
  private String mInterestGenre;
  private String mPublisherName;
  private String mDateOfPublication;
  private String mPublicationType;
  private String mPublicationWebLink;
  private String mPublicationISSN;
  private String mPublicationIssue;
  private String mPublicationVolume;
  private String mPublicationJournalName;
  private String mPublicationCountry;
  private String mPublicationPages;
  private String mPublicationStatus;
  private String mAppliedOn;
  private String mActionTakenOn;
  private String mLastModified;
  private int mRowNumber;

  public PersistentPublicationInformation() {}

  public PersistentPublicationInformation(PersistentPublicationInformation pPersistentPublicationInformation) {
    mId = pPersistentPublicationInformation.getId();
    mEmployeeId = pPersistentPublicationInformation.getEmployeeId();
    mPublicationTitle = pPersistentPublicationInformation.getPublicationTitle();
    mInterestGenre = pPersistentPublicationInformation.getInterestGenre();
    mPublisherName = pPersistentPublicationInformation.getPublisherName();
    mDateOfPublication = pPersistentPublicationInformation.getDateOfPublication();
    mPublicationType = pPersistentPublicationInformation.getPublicationType();
    mPublicationWebLink = pPersistentPublicationInformation.getPublicationWebLink();
    mPublicationISSN = pPersistentPublicationInformation.getPublicationISSN();
    mPublicationIssue = pPersistentPublicationInformation.getPublicationIssue();
    mPublicationVolume = pPersistentPublicationInformation.getPublicationVolume();
    mPublicationJournalName = pPersistentPublicationInformation.getPublicationJournalName();
    mPublicationCountry = pPersistentPublicationInformation.getPublicationCountry();
    mPublicationStatus = pPersistentPublicationInformation.getPublicationStatus();
    mPublicationPages = pPersistentPublicationInformation.getPublicationPages();
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
  public void setPublicationTitle(String pPublicationTitle) {
    mPublicationTitle = pPublicationTitle;
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
  public void setDateOfPublication(String pDateOfPublication) {
    mDateOfPublication = pDateOfPublication;
  }

  @Override
  public void setPublicationType(String pPublicationType) {
    mPublicationType = pPublicationType;
  }

  @Override
  public void setPublicationWebLink(String pPublicationWebLink) {
    mPublicationWebLink = pPublicationWebLink;
  }

  @Override
  public void setPublicationISSN(String pPublicationISSN) {
    mPublicationIssue = pPublicationISSN;
  }

  @Override
  public void setPublicationIssue(String pPublicationIssue) {
    mPublicationIssue = pPublicationIssue;
  }

  @Override
  public void setPublicationVolume(String pPublicationVolume) {
    mPublicationVolume = pPublicationVolume;
  }

  @Override
  public void setPublicationJournalName(String pPublicationJournalName) {
    mPublicationJournalName = pPublicationJournalName;
  }

  @Override
  public void setPublicationCountry(String pPublicationCountry) {
    mPublicationCountry = pPublicationCountry;
  }

  @Override
  public void setPublicationPages(String pPublicationPages) {
    mPublicationPages = pPublicationPages;
  }

  @Override
  public void setPublicationStatus(String pPublicationStatus) {
    mPublicationStatus = pPublicationStatus;
  }

  @Override
  public void setAppliedOn(String pAppliedOn) {
    mAppliedOn = pAppliedOn;
  }

  @Override
  public void setActionTakenOn(String pActionTakenOn) {
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
  public String getPublicationTitle() {
    return mPublicationTitle;
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
  public String getDateOfPublication() {
    return mDateOfPublication;
  }

  @Override
  public String getPublicationType() {
    return mPublicationType;
  }

  @Override
  public String getPublicationWebLink() {
    return mPublicationWebLink;
  }

  @Override
  public String getPublicationISSN() {
    return mPublicationISSN;
  }

  @Override
  public String getPublicationIssue() {
    return mPublicationIssue;
  }

  @Override
  public String getPublicationVolume() {
    return mPublicationVolume;
  }

  @Override
  public String getPublicationJournalName() {
    return mPublicationJournalName;
  }

  @Override
  public String getPublicationCountry() {
    return mPublicationCountry;
  }

  @Override
  public String getPublicationPages() {
    return mPublicationPages;
  }

  @Override
  public String getPublicationStatus() {
    return mPublicationStatus;
  }

  @Override
  public String getAppliedOn() {
    return mAppliedOn;
  }

  @Override
  public String getActionTakenOn() {
    return mActionTakenOn;
  }

  @Override
  public int getRowNumber() {
    return mRowNumber;
  }

}
