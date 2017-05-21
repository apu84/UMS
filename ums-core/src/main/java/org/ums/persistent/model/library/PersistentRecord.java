package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.dto.library.ImprintDto;
import org.ums.domain.model.dto.library.PhysicalDescriptionDto;
import org.ums.domain.model.immutable.library.MaterialContributor;
import org.ums.domain.model.immutable.library.Note;
import org.ums.domain.model.immutable.library.Subject;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.enums.common.Language;
import org.ums.enums.library.*;
import org.ums.manager.library.PublisherManager;
import org.ums.manager.library.RecordManager;

import java.util.List;

/**
 * Created by Ifti on 19-Feb-17.
 */
public class PersistentRecord implements MutableRecord {

  private static RecordManager sRecordManager;
  private static PublisherManager sPublisherManager;

  private Long mId;
  private Long mMfn;
  private Language mLanguage;
  private String mTitle;
  private String mSubTitle;
  private String mGmd;
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
  private String mCallDate;
  private String mAuthorMark;
  private List<MaterialContributor> mContributorList;
  private ImprintDto mImprint;
  private Long mPublisherId;
  private PhysicalDescriptionDto mPhysicalDescription;
  private MaterialType mMaterialType;
  private RecordStatus mRecordStatus;
  private BookBindingType mBookBindingType;
  private AcquisitionType mAcquisitionType;
  private String mKeywords;
  private List<Subject> mSubjectList;
  private List<Note> mNoteList;
  private String mLastModified;
  private String mDocumentalist;
  private String mEntryDate;
  private String mLastUpdatedOn;
  private String mLastUpdatedBy;

  private String mContributorJsonString;
  private String mNoteJsonString;
  private String mSubjectJsonString;
  private String mPhysicalDescriptionString;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sRecordManager = applicationContext.getBean("recordManager", RecordManager.class);
    sPublisherManager = applicationContext.getBean("publisherManager", PublisherManager.class);
  }

  public PersistentRecord() {}

  public PersistentRecord(final PersistentRecord pPersistentRecord) {
    mId = pPersistentRecord.getId();
    mMfn = pPersistentRecord.getMfn();
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
    mCallDate = pPersistentRecord.getCallDate();
    mAuthorMark = pPersistentRecord.getAuthorMark();
    mContributorList = pPersistentRecord.getContributorList();
    mImprint = pPersistentRecord.getImprint();
    mPublisherId = pPersistentRecord.getPublisherId();
    mPhysicalDescription = pPersistentRecord.getPhysicalDescription();
    mMaterialType = pPersistentRecord.getMaterialType();
    mRecordStatus = pPersistentRecord.getRecordStatus();
    mBookBindingType = pPersistentRecord.getBookBindingType();
    mAcquisitionType = pPersistentRecord.getAcquisitionType();
    mKeywords = pPersistentRecord.getKeyWords();
    mSubjectList = pPersistentRecord.getSubjectList();
    mNoteList = pPersistentRecord.getNoteList();
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
  public Long getMfn() {
    return mMfn;
  }

  @Override
  public void setMfn(Long pMfn) {
    mId = pMfn;
    mMfn = pMfn;
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
  public String getGmd() {
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
  public void setGmd(String pGmd) {
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
  public String getCallDate() {
    return mCallDate;
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
  public List<MaterialContributor> getContributorList() {
    return mContributorList;
  }

  @Override
  public void setTranslateTitleEdition(String pTranslateTitleEdition) {
    mTranslateTitleEdition = pTranslateTitleEdition;
  }

  @Override
  public ImprintDto getImprint() {

    if(mImprint.getPublisher() == null && ( mImprint.getPublisherId() !=null && mImprint.getPublisherId() != 0 ))
      mImprint.setPublisher(sPublisherManager.get(mImprint.getPublisherId()));

    return mImprint;

  }

  @Override
  public PhysicalDescriptionDto getPhysicalDescription() {
    return mPhysicalDescription;
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
  public BookBindingType getBookBindingType() {
    return mBookBindingType;
  }

  @Override
  public void setCallDate(String pCallDate) {
    mCallDate = pCallDate;
  }

  @Override
  public AcquisitionType getAcquisitionType() {
    return mAcquisitionType;
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
  public void setContributorList(List<MaterialContributor> pContributorList) {
    setContributorList(pContributorList);
  }

  @Override
  public List<Subject> getSubjectList() {
    return mSubjectList;
  }

  @Override
  public List<Note> getNoteList() {
    return mNoteList;
  }

  @Override
  public void setImprint(ImprintDto pImprint) {
    mImprint = pImprint;
  }

  @Override
  public void setPhysicalDescription(PhysicalDescriptionDto pPhysicalDescription) {
    mPhysicalDescription = pPhysicalDescription;
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
  public void setBookBindingType(BookBindingType pBookBindingType) {
    mBookBindingType = pBookBindingType;
  }

  @Override
  public void setAcquisitionType(AcquisitionType pAcquisitionType) {
    mAcquisitionType = pAcquisitionType;
  }

  @Override
  public void setKeyWords(String pKeywords) {
    mKeywords = pKeywords;
  }

  @Override
  public void setSubjectList(List<Subject> pSubjectList) {
    mSubjectList = pSubjectList;
  }

  @Override
  public void setNoteList(List<Note> pNoteList) {
    mNoteList = pNoteList;
  }

  @Override
  public String getDocumentalist() {
    return mDocumentalist;
  }

  @Override
  public String getEntryDate() {
    return mEntryDate;
  }

  @Override
  public String getLastUpdatedOn() {
    return mLastUpdatedOn;
  }

  @Override
  public void setDocumentalist(String pDocumentalist) {
    mDocumentalist = pDocumentalist;
  }

  @Override
  public void setEntryDate(String pEntryDate) {
    mEntryDate = pEntryDate;
  }

  @Override
  public void setLastUpdatedOn(String pLastUpdatedOn) {
    mLastUpdatedOn = pLastUpdatedOn;
  }

  @Override
  public String getLastUpdatedBy() {
    return mLastUpdatedBy;
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

  public Long getPublisherId() {
    return mPublisherId;
  }

  public void setPublisherId(Long pPublisherId) {
    this.mPublisherId = pPublisherId;
  }
}
