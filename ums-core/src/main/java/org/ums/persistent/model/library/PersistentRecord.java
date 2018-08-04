package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.dto.library.ImprintDto;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.enums.common.Language;
import org.ums.enums.library.GeneralMaterialDescription;
import org.ums.enums.library.JournalFrequency;
import org.ums.enums.library.MaterialType;
import org.ums.enums.library.RecordStatus;
import org.ums.manager.library.PublisherManager;
import org.ums.manager.library.RecordManager;

import java.util.Date;

/**
 * Created by Ifti on 19-Feb-17.
 */
public class PersistentRecord implements MutableRecord {

  private static RecordManager sRecordManager;
  private static PublisherManager sPublisherManager;

  private Long mId;
  private Language mLanguage;
  private String mTitle;
  private String mSubTitle;
  private GeneralMaterialDescription mGmd;
  private String mSeriesTitle;
  private String mVolumeNo;
  private String mVolumeTitle;
  private String mSerialIssueNo;
  private String mSerialNumber;
  private String mSerialSpecial;
  private String mLibraryLacks;
  private String mChangedTitle;
  private String mIsbn;
  private String mIssn;
  private String mCorpAuthorMain;
  private String mCorpSubBody;
  private String mCorpCityCountry;
  private String mEdition;
  private String mTranslateTitleEdition;
  private JournalFrequency mFrequency;
  private String mCallNo;
  private String mClassNo;
  private String mAuthorMark;
  private Integer mCallYear;
  private String mCallEdition;
  private String mCallVolume;
  private String mContributorJsonString;
  private ImprintDto mImprint;
  private String mPhysicalDescriptionString;
  private MaterialType mMaterialType;
  private RecordStatus mRecordStatus;
  private String mKeywords;
  private String mSubjectJsonString;
  private String mNoteJsonString;
  private Integer mTotalItems;
  private Integer mTotalAvailable;
  private Integer mTotalCheckedOut;
  private Integer mTotalOnHold;
  private String mDocumentalist;
  private Date mEntryDate;
  private Date mLastUpdatedOn;
  private String mLastUpdatedBy;
  private String mLastModified;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sRecordManager = applicationContext.getBean("recordManager", RecordManager.class);
    sPublisherManager = applicationContext.getBean("publisherManager", PublisherManager.class);
  }

  public PersistentRecord() {}

  public PersistentRecord(final PersistentRecord pPersistentRecord) {
    mId = pPersistentRecord.getId();
    mLanguage = pPersistentRecord.getLanguage();
    mTitle = pPersistentRecord.getTitle();
    mSubTitle = pPersistentRecord.getSubTitle();
    mGmd = pPersistentRecord.getGmd();
    mSeriesTitle = pPersistentRecord.getSeriesTitle();
    mVolumeNo = pPersistentRecord.getVolumeNo();
    mVolumeTitle = pPersistentRecord.getVolumeTitle();
    mSerialIssueNo = pPersistentRecord.getSerialIssueNo();
    mSerialNumber = pPersistentRecord.getSerialNumber();
    mSerialSpecial = pPersistentRecord.getSerialSpecial();
    mLibraryLacks = pPersistentRecord.getLibraryLacks();
    mChangedTitle = pPersistentRecord.getChangedTitle();
    mIsbn = pPersistentRecord.getIsbn();
    mIssn = pPersistentRecord.getIssn();
    mCorpAuthorMain = pPersistentRecord.getCorpAuthorMain();
    mCorpSubBody = pPersistentRecord.getCorpSubBody();
    mCorpCityCountry = pPersistentRecord.getCorpCityCountry();
    mEdition = pPersistentRecord.getEdition();
    mTranslateTitleEdition = pPersistentRecord.getTranslateTitleEdition();
    mFrequency = pPersistentRecord.getFrequency();
    mCallNo = pPersistentRecord.getCallNo();
    mClassNo = pPersistentRecord.getClassNo();
    mAuthorMark = pPersistentRecord.getAuthorMark();
    mCallYear = pPersistentRecord.getCallYear();
    mCallEdition = pPersistentRecord.getCallEdition();
    mCallVolume = pPersistentRecord.getCallVolume();
    mContributorJsonString = pPersistentRecord.getContributorJsonString();
    mImprint = pPersistentRecord.getImprint();
    mPhysicalDescriptionString = pPersistentRecord.getPhysicalDescriptionString();
    mMaterialType = pPersistentRecord.getMaterialType();
    mRecordStatus = pPersistentRecord.getRecordStatus();
    mKeywords = pPersistentRecord.getKeyWords();
    mSubjectJsonString = pPersistentRecord.getSubjectJsonString();
    mNoteJsonString = pPersistentRecord.getNoteJsonString();
    mTotalItems = pPersistentRecord.getTotalItems();
    mTotalAvailable = pPersistentRecord.getTotalAvailable();
    mTotalCheckedOut = pPersistentRecord.getTotalCheckedOut();
    mTotalOnHold = pPersistentRecord.getTotalOnHold();
    mDocumentalist = pPersistentRecord.getDocumentalist();
    mEntryDate = pPersistentRecord.getEntryDate();
    mLastUpdatedOn = pPersistentRecord.getLastUpdatedOn();
    mLastUpdatedBy = pPersistentRecord.getLastUpdatedBy();
    mLastModified = pPersistentRecord.getLastModified();

  }

  @Override
  public Long create() {
    return sRecordManager.create(this);
  }

  @Override
  public void update() {
    sRecordManager.update(this);
  }

  @Override
  public MutableRecord edit() {
    return new PersistentRecord(this);
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
    sRecordManager.delete(this);
  }

  @Override
  public Language getLanguage() {
    return mLanguage;
  }

  @Override
  public void setLanguage(Language pLanguage) {
    mLanguage = pLanguage;
  }

  @Override
  public String getTitle() {
    return mTitle;
  }

  @Override
  public String getSubTitle() {
    return mSubTitle;
  }

  @Override
  public void setTitle(String pTitle) {
    mTitle = pTitle;
  }

  @Override
  public GeneralMaterialDescription getGmd() {
    return mGmd;
  }

  @Override
  public void setSubTitle(String pSubTitle) {
    mSubTitle = pSubTitle;
  }

  @Override
  public String getSeriesTitle() {
    return mSeriesTitle;
  }

  @Override
  public void setGmd(GeneralMaterialDescription pGmd) {
    mGmd = pGmd;
  }

  @Override
  public String getVolumeNo() {
    return mVolumeNo;
  }

  @Override
  public void setSeriesTitle(String pSeriesTitle) {
    mSeriesTitle = pSeriesTitle;
  }

  @Override
  public String getVolumeTitle() {
    return mVolumeTitle;
  }

  @Override
  public String getSerialIssueNo() {
    return mSerialIssueNo;
  }

  @Override
  public void setVolumeNo(String pVolumeNo) {
    mVolumeNo = pVolumeNo;
  }

  @Override
  public String getSerialNumber() {
    return mSerialNumber;
  }

  @Override
  public void setVolumeTitle(String pVolumeTitle) {
    mVolumeTitle = pVolumeTitle;
  }

  @Override
  public String getSerialSpecial() {
    return mSerialSpecial;
  }

  @Override
  public void setSerialIssueNo(String pSerialIssueNo) {
    mSerialIssueNo = pSerialIssueNo;
  }

  @Override
  public String getLibraryLacks() {
    return mLibraryLacks;
  }

  @Override
  public String getChangedTitle() {
    return mChangedTitle;
  }

  @Override
  public void setSerialNumber(String pSerialNumber) {
    mSerialNumber = pSerialNumber;
  }

  @Override
  public String getIsbn() {
    return mIsbn;
  }

  @Override
  public String getIssn() {
    return mIssn;
  }

  @Override
  public void setSerialSpecial(String pSerialSpecial) {
    mSerialSpecial = pSerialSpecial;
  }

  @Override
  public String getCorpAuthorMain() {
    return mCorpAuthorMain;
  }

  @Override
  public void setLibraryLacks(String pLibraryLacks) {
    mLibraryLacks = pLibraryLacks;
  }

  @Override
  public String getCorpSubBody() {
    return mCorpSubBody;
  }

  @Override
  public String getCorpCityCountry() {
    return mCorpCityCountry;
  }

  @Override
  public void setChangedTitle(String pChangedTitle) {
    mChangedTitle = pChangedTitle;
  }

  @Override
  public String getEdition() {
    return mEdition;
  }

  @Override
  public void setIsbn(String pIsbn) {
    mIsbn = pIsbn;
  }

  @Override
  public String getTranslateTitleEdition() {
    return mTranslateTitleEdition;
  }

  @Override
  public void setIssn(String pIssn) {
    mIssn = pIssn;
  }

  @Override
  public JournalFrequency getFrequency() {
    return mFrequency;
  }

  @Override
  public void setCorpAuthorMain(String pCorpAuthorMain) {
    mCorpAuthorMain = pCorpAuthorMain;
  }

  @Override
  public String getCallNo() {
    return mCallNo;
  }

  @Override
  public void setCorpSubBody(String pCorpSubBody) {
    mCorpSubBody = pCorpSubBody;
  }

  @Override
  public String getClassNo() {
    return mClassNo;
  }

  @Override
  public Integer getCallYear() {
    return mCallYear;
  }

  @Override
  public String getCallEdition() {
    return mCallEdition;
  }

  @Override
  public String getCallVolume() {
    return mCallVolume;
  }

  @Override
  public void setCorpCityCountry(String pCorpCitCountry) {
    mCorpCityCountry = pCorpCitCountry;
  }

  @Override
  public String getAuthorMark() {
    return mAuthorMark;
  }

  @Override
  public void setEdition(String pEdition) {
    mEdition = pEdition;
  }

  @Override
  public void setTranslateTitleEdition(String pTranslateTitleEdition) {
    mTranslateTitleEdition = pTranslateTitleEdition;
  }

  @Override
  public ImprintDto getImprint() {
    if(mImprint.getPublisher() == null && (mImprint.getPublisherId() != null && mImprint.getPublisherId() != 0))
      mImprint.setPublisher(sPublisherManager.get(mImprint.getPublisherId()));
    return mImprint;
  }

  @Override
  public void setFrequency(JournalFrequency pFrequency) {
    mFrequency = pFrequency;
  }

  @Override
  public MaterialType getMaterialType() {
    return mMaterialType;
  }

  @Override
  public void setCallNo(String pCallNo) {
    mCallNo = pCallNo;
  }

  @Override
  public RecordStatus getRecordStatus() {
    return mRecordStatus;
  }

  @Override
  public void setClassNo(String pClassNo) {
    mClassNo = pClassNo;
  }

  @Override
  public void setCallYear(Integer pCallYear) {
    mCallYear = pCallYear;
  }

  @Override
  public void setCallEdition(String pCallEdition) {
    mCallEdition = pCallEdition;
  }

  @Override
  public void setCallVolume(String pCallVolume) {
    mCallVolume = pCallVolume;
  }

  @Override
  public void setAuthorMark(String pAuthorMark) {
    mAuthorMark = pAuthorMark;
  }

  @Override
  public String getKeyWords() {
    return mKeywords;
  }

  @Override
  public void setImprint(ImprintDto pImprint) {
    mImprint = pImprint;
  }

  @Override
  public void setMaterialType(MaterialType pMaterialType) {
    mMaterialType = pMaterialType;
  }

  @Override
  public void setRecordStatus(RecordStatus pRecordStatus) {
    mRecordStatus = pRecordStatus;
  }

  @Override
  public void setKeyWords(String pKeywords) {
    mKeywords = pKeywords;
  }

  @Override
  public String getDocumentalist() {
    return mDocumentalist;
  }

  @Override
  public Date getEntryDate() {
    return mEntryDate;
  }

  @Override
  public Date getLastUpdatedOn() {
    return mLastUpdatedOn;
  }

  @Override
  public void setDocumentalist(String pDocumentalist) {
    mDocumentalist = pDocumentalist;
  }

  @Override
  public void setEntryDate(Date pEntryDate) {
    mEntryDate = pEntryDate;
  }

  @Override
  public void setLastUpdatedOn(Date pLastUpdatedOn) {
    mLastUpdatedOn = pLastUpdatedOn;
  }

  @Override
  public String getLastUpdatedBy() {
    return mLastUpdatedBy;
  }

  @Override
  public Integer getTotalItems() {
    return mTotalItems;
  }

  @Override
  public Integer getTotalAvailable() {
    return mTotalAvailable;
  }

  @Override
  public Integer getTotalCheckedOut() {
    return mTotalCheckedOut;
  }

  @Override
  public Integer getTotalOnHold() {
    return mTotalOnHold;
  }

  @Override
  public void setLastUpdatedBy(String pLastUpdatedBy) {
    mLastUpdatedBy = pLastUpdatedBy;
  }

  @Override
  public String getContributorJsonString() {
    return mContributorJsonString;
  }

  @Override
  public String getSubjectJsonString() {
    return mSubjectJsonString;
  }

  @Override
  public String getNoteJsonString() {
    return mNoteJsonString;
  }

  @Override
  public void setContributorJsonString(String pContributorJsonString) {
    mContributorJsonString = pContributorJsonString;
  }

  public String getPhysicalDescriptionString() {
    return mPhysicalDescriptionString;
  }

  public void setPhysicalDescriptionString(String pPhysicalDescriptionString) {
    mPhysicalDescriptionString = pPhysicalDescriptionString;
  }

  @Override
  public void setSubjectJsonString(String pSubjectJsonString) {
    mSubjectJsonString = pSubjectJsonString;
  }

  @Override
  public void setNoteJsonString(String pNoteJsonString) {
    mNoteJsonString = pNoteJsonString;
  }

  @Override
  public void setTotalItems(Integer pTotalItems) {
    mTotalItems = pTotalItems;
  }

  @Override
  public void setTotalAvailable(Integer pTotalAvailable) {
    mTotalAvailable = pTotalAvailable;
  }

  @Override
  public void setTotalCheckedOut(Integer pTotalCheckedOut) {
    mTotalCheckedOut = pTotalCheckedOut;
  }

  @Override
  public void setTotalOnHold(Integer pTotalOnHold) {
    mTotalOnHold = pTotalOnHold;
  }
}
