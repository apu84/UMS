package org.ums.employee.personal;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.common.*;
import org.ums.enums.common.*;
import org.ums.enums.common.RelationType;
import org.ums.manager.common.*;
import org.ums.manager.registrar.PersonalInformationManager;

import java.util.Date;

public class PersistentPersonalInformation implements MutablePersonalInformation {

  private static PersonalInformationManager sPersonalInformationManager;
  // private static BloodGroupManager sBloodGroupManager;
  // private static NationalityManager sNationalityManager;
  // private static ReligionManager sReligionManager;
  // private static MaritalStatusManager sMaritalStatusManager;
  // private static RelationTypeManager sRelationTypeManager;
  private static CountryManager sCountryManager;
  private static DivisionManager sDivisionManager;
  private static DistrictManager sDistrictManager;
  private static ThanaManager sThanaManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sPersonalInformationManager =
        applicationContext.getBean("personalInformationManager", PersonalInformationManager.class);
    // sBloodGroupManager = applicationContext.getBean("bloodGroupManager",
    // BloodGroupManager.class);
    // sNationalityManager = applicationContext.getBean("nationalityManager",
    // NationalityManager.class);
    // sReligionManager = applicationContext.getBean("religionManager", ReligionManager.class);
    // sMaritalStatusManager = applicationContext.getBean("maritalStatusManager",
    // MaritalStatusManager.class);
    // sRelationTypeManager = applicationContext.getBean("relationTypeManager",
    // RelationTypeManager.class);
    sCountryManager = applicationContext.getBean("countryManager", CountryManager.class);
    sDivisionManager = applicationContext.getBean("divisionManager", DivisionManager.class);
    sDistrictManager = applicationContext.getBean("districtManager", DistrictManager.class);
    sThanaManager = applicationContext.getBean("thanaManager", ThanaManager.class);
  }

  private String mId;
  private String mFirstName;
  private String mLastName;
  private String mGender;
  private BloodGroupType mBloodGroup;
  private Integer mBloodGroupId;
  private String mFatherName;
  private String mMotherName;
  private NationalityType mNationality;
  private Integer mNationalityId;
  private ReligionType mReligion;
  private Integer mReligionId;
  private Date mDateOfBirth;
  private String mNidNo;
  private MaritalStatusType mMaritalStatus;
  private Integer mMaritalStatusId;
  private String mSpouseName;
  private String mSpouseNidNo;
  private String mWebsite;
  private String mOrganizationalEmail;
  private String mPersonalEmail;
  private String mMobileNumber;
  private String mPhoneNumber;
  private String mPresentAddressLine1;
  private String mPresentAddressLine2;
  private Country mPresentAddressCountry;
  private Integer mPresentAddressCountryId;
  private Division mPresentAddressDivision;
  private Integer mPresentAddressDivisionId;
  private District mPresentAddressDistrict;
  private Integer mPresentAddressDistrictId;
  private Thana mPresentAddressThana;
  private Integer mPresentAddressThanaId;
  private String mPresentAddressPostCode;
  private String mPermanentAddressLine1;
  private String mPermanentAddressLine2;
  private Country mPermanentAddressCountry;
  private Integer mPermanentAddressCountryId;
  private Division mPermanentAddressDivision;
  private Integer mPermanentAddressDivisionId;
  private District mPermanentAddressDistrict;
  private Integer mPermanentAddressDistrictId;
  private Thana mPermanentAddressThana;
  private Integer mPermanentAddressThanaId;
  private String mPermanentAddressPostCode;
  private String mEmergencyContactName;
  private RelationType mEmergencyContactRelation;
  private Integer mEmergencyContactRelationId;
  private String mEmergencyContactPhone;
  private String mEmergencyContactAddress;
  private String mLastModified;

  public PersistentPersonalInformation() {}

  public PersistentPersonalInformation(PersistentPersonalInformation pPersistentPersonalInformation) {
    mId = pPersistentPersonalInformation.getId();
    mFirstName = pPersistentPersonalInformation.getFirstName();
    mLastName = pPersistentPersonalInformation.getLastName();
    mGender = pPersistentPersonalInformation.getGender();
    mBloodGroup = pPersistentPersonalInformation.getBloodGroup();
    mBloodGroupId = pPersistentPersonalInformation.getBloodGroupId();
    mFatherName = pPersistentPersonalInformation.getFatherName();
    mMotherName = pPersistentPersonalInformation.getMotherName();
    mNationality = pPersistentPersonalInformation.getNationality();
    mNationalityId = pPersistentPersonalInformation.getNationalityId();
    mReligion = pPersistentPersonalInformation.getReligion();
    mReligionId = pPersistentPersonalInformation.getReligionId();
    mDateOfBirth = pPersistentPersonalInformation.getDateOfBirth();
    mNidNo = pPersistentPersonalInformation.getNidNo();
    mMaritalStatus = pPersistentPersonalInformation.getMaritalStatus();
    mMaritalStatusId = pPersistentPersonalInformation.getMaritalStatusId();
    mSpouseName = pPersistentPersonalInformation.getSpouseName();
    mSpouseNidNo = pPersistentPersonalInformation.getSpouseNidNo();
    mWebsite = pPersistentPersonalInformation.getWebsite();
    mOrganizationalEmail = pPersistentPersonalInformation.getOrganizationalEmail();
    mPersonalEmail = pPersistentPersonalInformation.getPersonalEmail();
    mMobileNumber = pPersistentPersonalInformation.getMobileNumber();
    mPhoneNumber = pPersistentPersonalInformation.getPhoneNumber();
    mPresentAddressLine1 = pPersistentPersonalInformation.getPresentAddressLine1();
    mPresentAddressLine2 = pPersistentPersonalInformation.getPresentAddressLine2();
    mPresentAddressCountry = pPersistentPersonalInformation.getPresentAddressCountry();
    mPresentAddressCountryId = pPersistentPersonalInformation.getPresentAddressCountryId();
    mPresentAddressDivision = pPersistentPersonalInformation.getPresentAddressDivision();
    mPresentAddressDivisionId = pPersistentPersonalInformation.getPresentAddressDivisionId();
    mPresentAddressDistrict = pPersistentPersonalInformation.getPresentAddressDistrict();
    mPresentAddressDistrictId = pPersistentPersonalInformation.getPresentAddressDistrictId();
    mPresentAddressThana = pPersistentPersonalInformation.getPresentAddressThana();
    mPresentAddressThanaId = pPersistentPersonalInformation.getPresentAddressThanaId();
    mPresentAddressPostCode = pPersistentPersonalInformation.getPresentAddressPostCode();
    mPermanentAddressLine1 = pPersistentPersonalInformation.getPermanentAddressLine1();
    mPermanentAddressLine1 = pPersistentPersonalInformation.getPermanentAddressLine2();
    mPermanentAddressCountry = pPersistentPersonalInformation.getPermanentAddressCountry();
    mPermanentAddressCountryId = pPersistentPersonalInformation.getPermanentAddressCountryId();
    mPermanentAddressDivision = pPersistentPersonalInformation.getPermanentAddressDivision();
    mPermanentAddressDivisionId = pPersistentPersonalInformation.getPermanentAddressDivisionId();
    mPermanentAddressDistrict = pPersistentPersonalInformation.getPermanentAddressDistrict();
    mPermanentAddressDistrictId = pPersistentPersonalInformation.getPermanentAddressDistrictId();
    mPermanentAddressThana = pPersistentPersonalInformation.getPermanentAddressThana();
    mPermanentAddressThanaId = pPersistentPersonalInformation.getPermanentAddressThanaId();
    mPermanentAddressPostCode = pPersistentPersonalInformation.getPermanentAddressPostCode();
    mEmergencyContactName = pPersistentPersonalInformation.getEmergencyContactName();
    mEmergencyContactRelation = pPersistentPersonalInformation.getEmergencyContactRelation();
    mEmergencyContactRelationId = pPersistentPersonalInformation.getEmergencyContactRelationId();
    mEmergencyContactPhone = pPersistentPersonalInformation.getEmergencyContactPhone();
    mEmergencyContactAddress = pPersistentPersonalInformation.getEmergencyContactAddress();
    mLastModified = pPersistentPersonalInformation.getLastModified();
  }

  @Override
  public String create() {
    return sPersonalInformationManager.create(this);
  }

  @Override
  public void update() {
    sPersonalInformationManager.update(this);
  }

  @Override
  public void delete() {
    sPersonalInformationManager.delete(this);
  }

  @Override
  public MutablePersonalInformation edit() {
    return new PersistentPersonalInformation(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public void setId(String pId) {
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
  public void setLastName(String pLastName) {
    mLastName = pLastName;
  }

  @Override
  public void setGender(String pGender) {
    mGender = pGender;
  }

  @Override
  public void setBloodGroup(BloodGroupType pBloodGroup) {
    mBloodGroup = pBloodGroup;
  }

  @Override
  public void setFatherName(String pFatherName) {
    mFatherName = pFatherName;
  }

  @Override
  public void setMotherName(String pMotherName) {
    mMotherName = pMotherName;
  }

  @Override
  public void setNationality(NationalityType pNationality) {
    mNationality = pNationality;
  }

  @Override
  public void setReligion(ReligionType pReligion) {
    mReligion = pReligion;
  }

  @Override
  public void setDateOfBirth(Date pDateOfBirth) {
    mDateOfBirth = pDateOfBirth;
  }

  @Override
  public void setNidNo(String pNidNo) {
    mNidNo = pNidNo;
  }

  @Override
  public void setMaritalStatus(MaritalStatusType pMaritalStatus) {
    mMaritalStatus = pMaritalStatus;
  }

  @Override
  public void setSpouseName(String pSpouseName) {
    mSpouseName = pSpouseName;
  }

  @Override
  public void setSpouseNidNo(String pSpouseNidNo) {
    mSpouseNidNo = pSpouseNidNo;
  }

  @Override
  public void setWebsite(String pWebsite) {
    mWebsite = pWebsite;
  }

  @Override
  public void setOrganizationalEmail(String pOrganizationEmail) {
    mOrganizationalEmail = pOrganizationEmail;
  }

  @Override
  public void setPersonalEmail(String pPersonalEmail) {
    mPersonalEmail = pPersonalEmail;
  }

  @Override
  public void setMobileNumber(String pMobileNumber) {
    mMobileNumber = pMobileNumber;
  }

  @Override
  public void setPhoneNumber(String pPhoneNumber) {
    mPhoneNumber = pPhoneNumber;
  }

  @Override
  public void setPresentAddressLine1(String pPresentAddressLine1) {
    mPresentAddressLine1 = pPresentAddressLine1;
  }

  @Override
  public void setPresentAddressLine2(String pPresentAddressLine2) {
    mPresentAddressLine2 = pPresentAddressLine2;
  }

  @Override
  public void setPresentAddressThana(Thana pPresentAddressThana) {
    mPresentAddressThana = pPresentAddressThana;
  }

  @Override
  public void setPresentAddressDistrict(District pPresentAddressDistrict) {
    mPresentAddressDistrict = pPresentAddressDistrict;
  }

  @Override
  public void setPresentAddressPostCode(String pPresentAddressPostCode) {
    mPresentAddressPostCode = pPresentAddressPostCode;
  }

  @Override
  public void setPresentAddressDivision(Division pPresentAddressDivision) {
    mPresentAddressDivision = pPresentAddressDivision;
  }

  @Override
  public void setPresentAddressCountry(Country pPresentAddressHouseCountry) {
    mPresentAddressCountry = pPresentAddressHouseCountry;
  }

  @Override
  public void setPermanentAddressLine1(String pPermanentAddressLine1) {
    mPermanentAddressLine1 = pPermanentAddressLine1;
  }

  @Override
  public void setPermanentAddressLine2(String pPermanentAddressLine2) {
    mPermanentAddressLine2 = pPermanentAddressLine2;
  }

  @Override
  public void setPermanentAddressThana(Thana pPermanentAddressThana) {
    mPermanentAddressThana = pPermanentAddressThana;
  }

  @Override
  public void setPermanentAddressDistrict(District pPermanentAddressDistrict) {
    mPermanentAddressDistrict = pPermanentAddressDistrict;
  }

  @Override
  public void setPermanentAddressPostCode(String pPermanentAddressPostCode) {
    mPermanentAddressPostCode = pPermanentAddressPostCode;
  }

  @Override
  public void setPermanentAddressDivision(Division pPermanentAddressDivision) {
    mPermanentAddressDivision = pPermanentAddressDivision;
  }

  @Override
  public void setPermanentAddressCountry(Country pPermanentAddressCountry) {
    mPermanentAddressCountry = pPermanentAddressCountry;
  }

  @Override
  public void setEmergencyContactName(String pEmergencyContactName) {
    mEmergencyContactName = pEmergencyContactName;
  }

  @Override
  public void setEmergencyContactRelation(RelationType pEmergencyContactRelation) {
    mEmergencyContactRelation = pEmergencyContactRelation;
  }

  @Override
  public void setEmergencyContactPhone(String pEmergencyContactPhone) {
    mEmergencyContactPhone = pEmergencyContactPhone;
  }

  @Override
  public void setEmergencyContactAddress(String pEmergencyContactAddress) {
    mEmergencyContactAddress = pEmergencyContactAddress;
  }

  @Override
  public String getFirstName() {
    return mFirstName;
  }

  @Override
  public String getLastName() {
    return mLastName;
  }

  @Override
  public String getGender() {
    return mGender;
  }

  @Override
  public BloodGroupType getBloodGroup() {
    return mBloodGroup;
  }

  @Override
  public String getFatherName() {
    return mFatherName;
  }

  @Override
  public String getMotherName() {
    return mMotherName;
  }

  @Override
  public NationalityType getNationality() {
    return mNationality;
  }

  @Override
  public ReligionType getReligion() {
    return mReligion;
  }

  @Override
  public Date getDateOfBirth() {
    return mDateOfBirth;
  }

  @Override
  public String getNidNo() {
    return mNidNo;
  }

  @Override
  public MaritalStatusType getMaritalStatus() {
    return mMaritalStatus;
  }

  @Override
  public String getSpouseName() {
    return mSpouseName;
  }

  @Override
  public String getSpouseNidNo() {
    return mSpouseNidNo;
  }

  @Override
  public String getWebsite() {
    return mWebsite;
  }

  @Override
  public String getOrganizationalEmail() {
    return mOrganizationalEmail;
  }

  @Override
  public String getPersonalEmail() {
    return mPersonalEmail;
  }

  @Override
  public String getMobileNumber() {
    return mMobileNumber;
  }

  @Override
  public String getPhoneNumber() {
    return mPhoneNumber;
  }

  @Override
  public String getPresentAddressLine1() {
    return mPresentAddressLine1;
  }

  @Override
  public String getPresentAddressLine2() {
    return mPresentAddressLine2;
  }

  @Override
  public Thana getPresentAddressThana() {
    return mPresentAddressThana;
  }

  @Override
  public District getPresentAddressDistrict() {
    return mPresentAddressDistrict;
  }

  @Override
  public String getPresentAddressPostCode() {
    return mPresentAddressPostCode;
  }

  @Override
  public Division getPresentAddressDivision() {
    return mPresentAddressDivision;
  }

  @Override
  public Country getPresentAddressCountry() {
    return mPresentAddressCountry;
  }

  @Override
  public String getPermanentAddressLine1() {
    return mPermanentAddressLine1;
  }

  @Override
  public String getPermanentAddressLine2() {
    return mPermanentAddressLine2;
  }

  @Override
  public Thana getPermanentAddressThana() {
    return mPermanentAddressThana;
  }

  @Override
  public District getPermanentAddressDistrict() {
    return mPermanentAddressDistrict;
  }

  @Override
  public String getPermanentAddressPostCode() {
    return mPermanentAddressPostCode;
  }

  @Override
  public Division getPermanentAddressDivision() {
    return mPermanentAddressDivision;
  }

  @Override
  public Country getPermanentAddressCountry() {
    return mPermanentAddressCountry;
  }

  @Override
  public String getEmergencyContactName() {
    return mEmergencyContactName;
  }

  @Override
  public RelationType getEmergencyContactRelation() {
    return mEmergencyContactRelation;
  }

  @Override
  public String getEmergencyContactPhone() {
    return mEmergencyContactPhone;
  }

  @Override
  public String getEmergencyContactAddress() {
    return mEmergencyContactAddress;
  }

  @Override
  public void setBloodGroupId(Integer pBloodGroupId) {
    mBloodGroupId = pBloodGroupId;
  }

  @Override
  public void setNationalityId(Integer pNationalityId) {
    mNationalityId = pNationalityId;
  }

  @Override
  public void setReligionId(Integer pReligionId) {
    mReligionId = pReligionId;
  }

  @Override
  public void setEmergencyContactRelationId(Integer pEmergencyContactRelationId) {
    mEmergencyContactRelationId = pEmergencyContactRelationId;
  }

  @Override
  public void setMaritalStatusId(Integer pMaritalStatusId) {
    mMaritalStatusId = pMaritalStatusId;
  }

  @Override
  public void setPresentAddressCountryId(Integer pPresentAddressCountryId) {
    mPresentAddressCountryId = pPresentAddressCountryId;
  }

  @Override
  public void setPresentAddressDivisionId(Integer pPresentAddressDivisionId) {
    mPresentAddressDivisionId = pPresentAddressDivisionId;
  }

  @Override
  public void setPresentAddressDistrictId(Integer pPresentAddressDistrictId) {
    mPresentAddressDistrictId = pPresentAddressDistrictId;
  }

  @Override
  public void setPresentAddressThanaId(Integer pPresentAddressThanaId) {
    mPresentAddressThanaId = pPresentAddressThanaId;
  }

  @Override
  public void setPermanentAddressCountryId(Integer pPermanentAddressCountryId) {
    mPermanentAddressCountryId = pPermanentAddressCountryId;
  }

  @Override
  public void setPermanentAddressDivisionId(Integer pPermanentAddressDivisionId) {
    mPermanentAddressDivisionId = pPermanentAddressDivisionId;
  }

  @Override
  public void setPermanentAddressDistrictId(Integer pPermanentAddressDistrictId) {
    mPermanentAddressDistrictId = pPermanentAddressDistrictId;
  }

  @Override
  public void setPermanentAddressThanaId(Integer pPermanentAddressThanaId) {
    mPermanentAddressThanaId = pPermanentAddressThanaId;
  }

  @Override
  public Integer getBloodGroupId() {
    return mBloodGroupId;
  }

  @Override
  public Integer getNationalityId() {
    return mNationalityId;
  }

  @Override
  public Integer getReligionId() {
    return mReligionId;
  }

  @Override
  public Integer getMaritalStatusId() {
    return mMaritalStatusId;
  }

  @Override
  public Integer getEmergencyContactRelationId() {
    return mEmergencyContactRelationId;
  }

  @Override
  public Integer getPresentAddressCountryId() {
    return mPresentAddressCountryId;
  }

  @Override
  public Integer getPresentAddressDivisionId() {
    return mPresentAddressDivisionId;
  }

  @Override
  public Integer getPresentAddressDistrictId() {
    return mPresentAddressDistrictId;
  }

  @Override
  public Integer getPresentAddressThanaId() {
    return mPresentAddressThanaId;
  }

  @Override
  public Integer getPermanentAddressCountryId() {
    return mPermanentAddressCountryId;
  }

  @Override
  public Integer getPermanentAddressDivisionId() {
    return mPermanentAddressDivisionId;
  }

  @Override
  public Integer getPermanentAddressDistrictId() {
    return mPermanentAddressDistrictId;
  }

  @Override
  public Integer getPermanentAddressThanaId() {
    return mPermanentAddressThanaId;
  }

}
