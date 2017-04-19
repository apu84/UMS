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
  private String mAuthor;
  private String mPublisherName;
  private String mDateOfPublication;
  private String mPublicationType;
  private String mPublicationWebLink;
  private String mLastModified;

  public PersistentPublicationInformation() {}

  public PersistentPublicationInformation(PersistentPublicationInformation pPersistentPublicationInformation) {
    mEmployeeId = pPersistentPublicationInformation.getEmployeeId();
    mPublicationTitle = pPersistentPublicationInformation.getPublicationTitle();
    mInterestGenre = pPersistentPublicationInformation.getInterestGenre();
    mPublisherName = pPersistentPublicationInformation.getPublisherName();
    mDateOfPublication = pPersistentPublicationInformation.getDateOfPublication();
    mPublicationType = pPersistentPublicationInformation.getPublicationType();
    mPublicationWebLink = pPersistentPublicationInformation.getPublicationWebLink();
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
}
