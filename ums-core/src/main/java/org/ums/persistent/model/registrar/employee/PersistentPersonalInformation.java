package org.ums.persistent.model.registrar.employee;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.registrar.employee.PersonalInformation;
import org.ums.domain.model.mutable.registrar.employee.MutablePersonalInformation;
import org.ums.manager.registrar.employee.PersonalInformationManager;

public class PersistentPersonalInformation implements MutablePersonalInformation {

  private static PersonalInformationManager sPersonalInformationManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sPersonalInformationManager =
        applicationContext.getBean("personalInformationManager", PersonalInformationManager.class);
  }

  private int mId;
  private int mEmployeeId;
  private String mFirstName;
  private String mLastName;
  private String mGender;
  private String mBloodGroup;
  private String mFatherName;
  private String mMotherName;
  private String mNationality;
  private String mReligion;
  private String mDateOfBirth;
  private int mNationalId;
  private int mMaritalStatus;
  private String mSpouseName;
  private int mSpouseNationalId;
  private String mWebsite;
  private String mOrganizationalEmail;
  private String mPersonalEmail;
  private String mMobileNumber;
  private String mPhoneNumber;
  private String mPresentAddressHouse;
  private String mPresentAddressRoad;
  private String mPresentAddressThana;
  private String mPresentAddressDistrict;
  private String mPresentAddressZip;
  private String mPresentAddressDivision;
  private String mPresentAddressCountry;
  private String mPermanentAddressHouse;
  private String mPermanentAddressRoad;
  private String mPermanentAddressThana;
  private String mPermanentAddressDistrict;
  private String mPermanentAddressZip;
  private String mPermanentAddressDivision;
  private String mPermanentAddressCountry;
  private String mEmergencyContactName;
  private String mEmergencyContactRelation;
  private String mEmergencyContactPhone;
  private String mEmergencyContactAddress;
  private String mLastModified;

  public PersistentPersonalInformation() {}

  public PersistentPersonalInformation(PersonalInformation pPersonalInformation) {
    mEmployeeId = pPersonalInformation.getEmployeeId();
    mFirstName = pPersonalInformation.getFirstName();
    mLastName = pPersonalInformation.getLastName();
    mGender = pPersonalInformation.getGender();
    mBloodGroup = pPersonalInformation.getBloodGroup();
    mFatherName = pPersonalInformation.getFatherName();
    mMotherName = pPersonalInformation.getMotherName();
    mNationality = pPersonalInformation.getNationality();
    mReligion = pPersonalInformation.getReligion();
    mDateOfBirth = pPersonalInformation.getDateOfBirth();
    mNationalId = pPersonalInformation.getNationalId();
    mMaritalStatus = pPersonalInformation.getMaritalStatus();
    mSpouseName = pPersonalInformation.getSpouseName();
    mSpouseNationalId = pPersonalInformation.getSpouseNationalId();
    mWebsite = pPersonalInformation.getWebsite();
    mOrganizationalEmail = pPersonalInformation.getOrganizationalEmail();
    mPersonalEmail = pPersonalInformation.getPersonalEmail();
    mMobileNumber = pPersonalInformation.getMobileNumber();
    mPhoneNumber = pPersonalInformation.getPhoneNumber();
    mPresentAddressHouse = pPersonalInformation.getPresentAddressHouse();
    mPresentAddressRoad = pPersonalInformation.getPresentAddressRoad();
    mPresentAddressThana = pPersonalInformation.getPresentAddressThana();
    mPresentAddressDistrict = pPersonalInformation.getPresentAddressDistrict();
    mPresentAddressZip = pPersonalInformation.getPresentAddressZip();
    mPresentAddressDivision = pPersonalInformation.getPresentAddressDivision();
    mPresentAddressCountry = pPersonalInformation.getPresentAddressCountry();
    mPermanentAddressHouse = pPersonalInformation.getPermanentAddressHouse();
    mPermanentAddressRoad = pPersonalInformation.getPermanentAddressRoad();
    mPermanentAddressThana = pPersonalInformation.getPermanentAddressThana();
    mPermanentAddressDistrict = pPersonalInformation.getPermanentAddressDistrict();
    mPermanentAddressZip = pPersonalInformation.getPermanentAddressZip();
    mPermanentAddressDivision = pPersonalInformation.getPermanentAddressDivision();
    mPermanentAddressCountry = pPersonalInformation.getPermanentAddressCountry();
    mEmergencyContactName = pPersonalInformation.getEmergencyContactName();
    mEmergencyContactRelation = pPersonalInformation.getEmergencyContactRelation();
    mEmergencyContactPhone = pPersonalInformation.getEmergencyContactPhone();
    mEmergencyContactAddress = pPersonalInformation.getEmergencyContactAddress();
  }

  @Override
  public Integer create() {
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
  public void setEmployeeId(int pEmployeeId) {
    mEmployeeId = pEmployeeId;
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
  public void setBloodGroup(String pBloodGroup) {
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
  public void setNationality(String pNationality) {
    mNationality = pNationality;
  }

  @Override
  public void setReligion(String pReligion) {
    mReligion = pReligion;
  }

  @Override
  public void setDateOfBirth(String pDateOfBirth) {
    mDateOfBirth = pDateOfBirth;
  }

  @Override
  public void setNationalId(int pNationalId) {
    mNationalId = pNationalId;
  }

  @Override
  public void setMaritalStatus(int pMaritalStatus) {
    mMaritalStatus = pMaritalStatus;
  }

  @Override
  public void setSpouseName(String pSpouseName) {
    mSpouseName = pSpouseName;
  }

  @Override
  public void setSpouseNationalId(int pSpouseNationalId) {
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
  public void setMobileNumber(String pMobileNumber) {
    mMobileNumber = pMobileNumber;
  }

  @Override
  public void setPhoneNumber(String pPhoneNumber) {
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
  public void setPresentAddressThana(String pPresentAddressThana) {
    mPresentAddressThana = pPresentAddressThana;
  }

  @Override
  public void setPresentAddressDistrict(String pPresentAddressDistrict) {
    mPresentAddressDistrict = pPresentAddressDistrict;
  }

  @Override
  public void setPresentAddressZip(String pPresentAddressZip) {
    mPresentAddressZip = pPresentAddressZip;
  }

  @Override
  public void setPresentAddressDivision(String pPresentAddressDivision) {
    mPresentAddressDivision = pPresentAddressDivision;
  }

  @Override
  public void setPresentAddressCountry(String pPresentAddressHouseCountry) {
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
  public void setPermanentAddressThana(String pPermanentAddressThana) {
    mPermanentAddressThana = pPermanentAddressThana;
  }

  @Override
  public void setPermanentAddressDistrict(String pPermanentAddressDistrict) {
    mPermanentAddressDistrict = pPermanentAddressDistrict;
  }

  @Override
  public void setPermanentAddressZip(String pPermanentAddressZip) {
    mPermanentAddressZip = pPermanentAddressZip;
  }

  @Override
  public void setPermanentAddressDivision(String pPermanentAddressDivision) {
    mPermanentAddressDivision = pPermanentAddressDivision;
  }

  @Override
  public void setPermanentAddressCountry(String pPermanentAddressCountry) {
    mPermanentAddressCountry = pPermanentAddressCountry;
  }

  @Override
  public void setEmergencyContactName(String pEmergencyContactName) {
    mEmergencyContactName = pEmergencyContactName;
  }

  @Override
  public void setEmergencyContactRelation(String pEmergencyContactRelation) {
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
  public int getEmployeeId() {
    return mEmployeeId;
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
  public String getBloodGroup() {
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
  public String getNationality() {
    return mNationality;
  }

  @Override
  public String getReligion() {
    return mReligion;
  }

  @Override
  public String getDateOfBirth() {
    return mDateOfBirth;
  }

  @Override
  public int getNationalId() {
    return mNationalId;
  }

  @Override
  public int getMaritalStatus() {
    return mMaritalStatus;
  }

  @Override
  public String getSpouseName() {
    return mSpouseName;
  }

  @Override
  public int getSpouseNationalId() {
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
  public String getMobileNumber() {
    return mMobileNumber;
  }

  @Override
  public String getPhoneNumber() {
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
  public String getPresentAddressThana() {
    return mPresentAddressThana;
  }

  @Override
  public String getPresentAddressDistrict() {
    return mPresentAddressDistrict;
  }

  @Override
  public String getPresentAddressZip() {
    return mPresentAddressZip;
  }

  @Override
  public String getPresentAddressDivision() {
    return mPresentAddressDivision;
  }

  @Override
  public String getPresentAddressCountry() {
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
  public String getPermanentAddressThana() {
    return mPermanentAddressThana;
  }

  @Override
  public String getPermanentAddressDistrict() {
    return mPermanentAddressDistrict;
  }

  @Override
  public String getPermanentAddressZip() {
    return mPermanentAddressZip;
  }

  @Override
  public String getPermanentAddressDivision() {
    return mPermanentAddressDivision;
  }

  @Override
  public String getPermanentAddressCountry() {
    return mPermanentAddressCountry;
  }

  @Override
  public String getEmergencyContactName() {
    return mEmergencyContactName;
  }

  @Override
  public String getEmergencyContactRelation() {
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
}
