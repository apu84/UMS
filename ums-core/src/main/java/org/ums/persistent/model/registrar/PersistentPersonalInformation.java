package org.ums.persistent.model.registrar;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.common.*;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.manager.common.*;
import org.ums.manager.registrar.PersonalInformationManager;

import java.util.Date;

public class PersistentPersonalInformation implements MutablePersonalInformation {

  private static PersonalInformationManager sPersonalInformationManager;
  private static BloodGroupManager sBloodGroupManager;
  private static NationalityManager sNationalityManager;
  private static ReligionManager sReligionManager;
  private static MaritalStatusManager sMaritalStatusManager;
  private static RelationTypeManager sRelationTypeManager;
  private static CountryManager sCountryManager;
  private static DivisionManager sDivisionManager;
  private static DistrictManager sDistrictManager;
  private static ThanaManager sThanaManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sPersonalInformationManager =
        applicationContext.getBean("personalInformationManager", PersonalInformationManager.class);
    sBloodGroupManager = applicationContext.getBean("bloodGroupManager", BloodGroupManager.class);
    sNationalityManager = applicationContext.getBean("nationalityManager", NationalityManager.class);
    sReligionManager = applicationContext.getBean("religionManager", ReligionManager.class);
    sMaritalStatusManager = applicationContext.getBean("maritalStatusManager", MaritalStatusManager.class);
    sRelationTypeManager = applicationContext.getBean("relationTypeManager", RelationTypeManager.class);
    sCountryManager = applicationContext.getBean("countryManager", CountryManager.class);
    sDivisionManager = applicationContext.getBean("divisionManager", DivisionManager.class);
    sDistrictManager = applicationContext.getBean("districtManager", DistrictManager.class);
    sThanaManager = applicationContext.getBean("thanaManager", ThanaManager.class);
  }

  private String mId;
  private String mFirstName;
  private String mLastName;
  private String mGender;
  private BloodGroup mBloodGroup;
  private Integer mBloodGroupId;
  private String mFatherName;
  private String mMotherName;
  private Nationality mNationality;
  private Integer mNationalityId;
  private Religion mReligion;
  private Integer mReligionId;
  private Date mDateOfBirth;
  private Integer mNationalId;
  private MaritalStatus mMaritalStatus;
  private Integer mMaritalStatusId;
  private String mSpouseName;
  private Integer mSpouseNationalId;
  private String mWebsite;
  private String mOrganizationalEmail;
  private String mPersonalEmail;
  private Integer mMobileNumber;
  private Integer mPhoneNumber;
  private String mPresentAddressHouse;
  private String mPresentAddressRoad;
  private Country mPresentAddressCountry;
  private Integer mPresentAddressCountryId;
  private Division mPresentAddressDivision;
  private Integer mPresentAddressDivisionId;
  private District mPresentAddressDistrict;
  private Integer mPresentAddressDistrictId;
  private Thana mPresentAddressThana;
  private Integer mPresentAddressThanaId;
  private Integer mPresentAddressZip;
  private String mPermanentAddressHouse;
  private String mPermanentAddressRoad;
  private Country mPermanentAddressCountry;
  private Integer mPermanentAddressCountryId;
  private Division mPermanentAddressDivision;
  private Integer mPermanentAddressDivisionId;
  private District mPermanentAddressDistrict;
  private Integer mPermanentAddressDistrictId;
  private Thana mPermanentAddressThana;
  private Integer mPermanentAddressThanaId;
  private Integer mPermanentAddressZip;
  private String mEmergencyContactName;
  private RelationType mEmergencyContactRelation;
  private Integer mEmergencyContactRelationId;
  private Integer mEmergencyContactPhone;
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
    mNationalId = pPersistentPersonalInformation.getNationalId();
    mMaritalStatus = pPersistentPersonalInformation.getMaritalStatus();
    mMaritalStatusId = pPersistentPersonalInformation.getMaritalStatusId();
    mSpouseName = pPersistentPersonalInformation.getSpouseName();
    mSpouseNationalId = pPersistentPersonalInformation.getSpouseNationalId();
    mWebsite = pPersistentPersonalInformation.getWebsite();
    mOrganizationalEmail = pPersistentPersonalInformation.getOrganizationalEmail();
    mPersonalEmail = pPersistentPersonalInformation.getPersonalEmail();
    mMobileNumber = pPersistentPersonalInformation.getMobileNumber();
    mPhoneNumber = pPersistentPersonalInformation.getPhoneNumber();
    mPresentAddressHouse = pPersistentPersonalInformation.getPresentAddressHouse();
    mPresentAddressRoad = pPersistentPersonalInformation.getPresentAddressRoad();
    mPresentAddressThana = pPersistentPersonalInformation.getPresentAddressThana();
    mPresentAddressThanaId = pPersistentPersonalInformation.getPresentAddressThanaId();
    mPresentAddressDistrict = pPersistentPersonalInformation.getPresentAddressDistrict();
    mPresentAddressDistrictId = pPersistentPersonalInformation.getPresentAddressDistrictId();
    mPresentAddressZip = pPersistentPersonalInformation.getPresentAddressZip();
    mPresentAddressDivision = pPersistentPersonalInformation.getPresentAddressDivision();
    mPresentAddressDivisionId = pPersistentPersonalInformation.getPresentAddressDivisionId();
    mPresentAddressCountry = pPersistentPersonalInformation.getPresentAddressCountry();
    mPresentAddressCountryId = pPersistentPersonalInformation.getPresentAddressCountryId();
    mPermanentAddressHouse = pPersistentPersonalInformation.getPermanentAddressHouse();
    mPermanentAddressRoad = pPersistentPersonalInformation.getPermanentAddressRoad();
    mPermanentAddressThana = pPersistentPersonalInformation.getPermanentAddressThana();
    mPermanentAddressThanaId = pPersistentPersonalInformation.getPermanentAddressThanaId();
    mPermanentAddressDistrict = pPersistentPersonalInformation.getPermanentAddressDistrict();
    mPermanentAddressDistrictId = pPersistentPersonalInformation.getPermanentAddressDistrictId();
    mPermanentAddressZip = pPersistentPersonalInformation.getPermanentAddressZip();
    mPermanentAddressDivision = pPersistentPersonalInformation.getPermanentAddressDivision();
    mPermanentAddressDivisionId = pPersistentPersonalInformation.getPermanentAddressDivisionId();
    mPermanentAddressCountry = pPersistentPersonalInformation.getPermanentAddressCountry();
    mPermanentAddressCountryId = pPersistentPersonalInformation.getPermanentAddressCountryId();
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
  public void setBloodGroup(BloodGroup pBloodGroup) {
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
  public void setNationality(Nationality pNationality) {
    mNationality = pNationality;
  }

  @Override
  public void setReligion(Religion pReligion) {
    mReligion = pReligion;
  }

  @Override
  public void setDateOfBirth(Date pDateOfBirth) {
    mDateOfBirth = pDateOfBirth;
  }

  @Override
  public void setNationalId(Integer pNationalId) {
    mNationalId = pNationalId;
  }

  @Override
  public void setMaritalStatus(MaritalStatus pMaritalStatus) {
    mMaritalStatus = pMaritalStatus;
  }

  @Override
  public void setSpouseName(String pSpouseName) {
    mSpouseName = pSpouseName;
  }

  @Override
  public void setSpouseNationalId(Integer pSpouseNationalId) {
    mSpouseNationalId = pSpouseNationalId;
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
  public void setMobileNumber(Integer pMobileNumber) {
    mMobileNumber = pMobileNumber;
  }

  @Override
  public void setPhoneNumber(Integer pPhoneNumber) {
    mPhoneNumber = pPhoneNumber;
  }

  @Override
  public void setPresentAddressHouse(String pPresentAddressHouse) {
    mPresentAddressHouse = pPresentAddressHouse;
  }

  @Override
  public void setPresentAddressRoad(String pPresentAddressRoad) {
    mPresentAddressRoad = pPresentAddressRoad;
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
  public void setPresentAddressZip(Integer pPresentAddressZip) {
    mPresentAddressZip = pPresentAddressZip;
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
  public void setPermanentAddressHouse(String pPermanentAddressHouse) {
    mPermanentAddressHouse = pPermanentAddressHouse;
  }

  @Override
  public void setPermanentAddressRoad(String pPermanentAddressRoad) {
    mPermanentAddressRoad = pPermanentAddressRoad;
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
  public void setPermanentAddressZip(Integer pPermanentAddressZip) {
    mPermanentAddressZip = pPermanentAddressZip;
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
  public void setEmergencyContactPhone(Integer pEmergencyContactPhone) {
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
  public BloodGroup getBloodGroup() {
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
  public Nationality getNationality() {
    return mNationality;
  }

  @Override
  public Religion getReligion() {
    return mReligion;
  }

  @Override
  public Date getDateOfBirth() {
    return mDateOfBirth;
  }

  @Override
  public Integer getNationalId() {
    return mNationalId;
  }

  @Override
  public MaritalStatus getMaritalStatus() {
    return mMaritalStatus;
  }

  @Override
  public String getSpouseName() {
    return mSpouseName;
  }

  @Override
  public Integer getSpouseNationalId() {
    return mSpouseNationalId;
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
  public Integer getMobileNumber() {
    return mMobileNumber;
  }

  @Override
  public Integer getPhoneNumber() {
    return mPhoneNumber;
  }

  @Override
  public String getPresentAddressHouse() {
    return mPresentAddressHouse;
  }

  @Override
  public String getPresentAddressRoad() {
    return mPresentAddressRoad;
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
  public Integer getPresentAddressZip() {
    return mPresentAddressZip;
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
  public String getPermanentAddressHouse() {
    return mPermanentAddressHouse;
  }

  @Override
  public String getPermanentAddressRoad() {
    return mPermanentAddressRoad;
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
  public Integer getPermanentAddressZip() {
    return mPermanentAddressZip;
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
  public Integer getEmergencyContactPhone() {
    return mEmergencyContactPhone;
  }

  @Override
  public String getEmergencyContactAddress() {
    return mEmergencyContactAddress;
  }

  public void setBloodGroupId(Integer pBloodGroupId) {
    mBloodGroupId = pBloodGroupId;
  }

  public void setNationalityId(Integer pNationalityId) {
    mNationalityId = pNationalityId;
  }

  public void setReligionId(Integer pReligionId) {
    mReligionId = pReligionId;
  }

  public void setEmergencyContactRelationId(Integer pEmergencyContactRelationId) {
    mEmergencyContactRelationId = pEmergencyContactRelationId;
  }

  public void setMaritalStatusId(Integer pMaritalStatusId) {
    mMaritalStatusId = pMaritalStatusId;
  }

  public void setPresentAddressCountryId(Integer pPresentAddressCountryId) {
    mPresentAddressCountryId = pPresentAddressCountryId;
  }

  public void setPresentAddressDivisionId(Integer pPresentAddressDivisionId) {
    mPresentAddressDivisionId = pPresentAddressDivisionId;
  }

  public void setPresentAddressDistrictId(Integer pPresentAddressDistrictId) {
    mPresentAddressDistrictId = pPresentAddressDistrictId;
  }

  public void setPresentAddressThanaId(Integer pPresentAddressThanaId) {
    mPresentAddressThanaId = pPresentAddressThanaId;
  }

  public void setPermanentAddressCountryId(Integer pPermanentAddressCountryId) {
    mPermanentAddressCountryId = pPermanentAddressCountryId;
  }

  public void setPermanentAddressDivisionId(Integer pPermanentAddressDivisionId) {
    mPermanentAddressDivisionId = pPermanentAddressDivisionId;
  }

  public void setPermanentAddressDistrictId(Integer pPermanentAddressDistrictId) {
    mPermanentAddressDistrictId = pPermanentAddressDistrictId;
  }

  public void setPermanentAddressThanaId(Integer pPermanentAddressThanaId) {
    mPermanentAddressThanaId = pPermanentAddressThanaId;
  }

  public Integer getBloodGroupId() {
    return mBloodGroupId;
  }

  public Integer getNationalityId() {
    return mNationalityId;
  }

  public Integer getReligionId() {
    return mReligionId;
  }

  public Integer getMaritalStatusId() {
    return mMaritalStatusId;
  }

  public Integer getEmergencyContactRelationId() {
    return mEmergencyContactRelationId;
  }

  public Integer getPresentAddressCountryId() {
    return mPresentAddressCountryId;
  }

  public Integer getPresentAddressDivisionId() {
    return mPresentAddressDivisionId;
  }

  public Integer getPresentAddressDistrictId() {
    return mPresentAddressDistrictId;
  }

  public Integer getPresentAddressThanaId() {
    return mPresentAddressThanaId;
  }

  public Integer getPermanentAddressCountryId() {
    return mPermanentAddressCountryId;
  }

  public Integer getPermanentAddressDivisionId() {
    return mPermanentAddressDivisionId;
  }

  public Integer getPermanentAddressDistrictId() {
    return mPermanentAddressDistrictId;
  }

  public Integer getPermanentAddressThanaId() {
    return mPermanentAddressThanaId;
  }

}
