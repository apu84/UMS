package org.ums.usermanagement.userView;

import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Department;
import org.ums.manager.DepartmentManager;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.role.RoleManager;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.UserManager;

import java.util.Date;

public class PersistentUserView implements MutableUserView {
  private static UserViewManager sUserManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sUserManager = applicationContext.getBean("userViewManager", UserViewManager.class);
  }

  private String mId;
  private String mUserName;
  private String mGender;
  private Date mDateOfBirth;
  private String mBloodGroup;
  private String mFatherName;
  private String mMotherName;
  private String mMobileNumber;
  private String mEmailAddress;
  private String mDepartment;
  private int mDesignation;
  private int mRoleId;
  private int mStatus;

  public PersistentUserView() {

  }

  public PersistentUserView(final PersistentUserView persistentUserView) {
    mId = persistentUserView.getId();
    mUserName = persistentUserView.getUserName();
    mGender = persistentUserView.getGender();
    mDateOfBirth = persistentUserView.getDateOfBirth();
    mDepartment = persistentUserView.getDepartment();
    mBloodGroup = persistentUserView.getBloodGroup();
    mFatherName = persistentUserView.getFatherName();
    mMotherName = persistentUserView.getMotherName();
    mMobileNumber = persistentUserView.getMobileNumber();
    mEmailAddress = persistentUserView.getEmailAddress();
    mDesignation = persistentUserView.getDesignation();
    mRoleId = persistentUserView.getRoleId();
    mStatus = persistentUserView.getStatus();
  }

  @Override
  public String create() {
    return sUserManager.create(this);
  }

  @Override
  public void update() {
    sUserManager.update(this);
  }

  @Override
  public void delete() {
    sUserManager.delete(this);
  }

  @Override
  public MutableUserView edit() {
    return new PersistentUserView(this);
  }

  @Override
  public void setUserName(String pUserName) {
    mUserName = pUserName;
  }

  @Override
  public void setGender(String pGender) {
    mGender = pGender;
  }

  @Override
  public void setDateOfBirth(Date pDateOfBirth) {
    mDateOfBirth = pDateOfBirth;
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
  public void setMobileNumber(String pMobileNumber) {
    mMobileNumber = pMobileNumber;
  }

  @Override
  public void setEmailAddress(String pEmailAddress) {
    mEmailAddress = pEmailAddress;
  }

  @Override
  public void setDepartment(String pDepartment) {
    mDepartment = pDepartment;
  }

  @Override
  public void setDesignation(int pDesignation) {
    mDesignation = pDesignation;
  }

  @Override
  public void setRoleId(int pRoleId) {
    mRoleId = pRoleId;
  }

  @Override
  public void setStatus(int pStatus) {
    mStatus = pStatus;
  }

  @Override
  public void setId(String pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {}

  @Override
  public String getUserName() {
    return mUserName;
  }

  @Override
  public String getGender() {
    return mGender;
  }

  @Override
  public Date getDateOfBirth() {
    return mDateOfBirth;
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
  public String getMobileNumber() {
    return mMobileNumber;
  }

  @Override
  public String getEmailAddress() {
    return mEmailAddress;
  }

  @Override
  public String getDepartment() {
    return mDepartment;
  }

  @Override
  public int getDesignation() {
    return mDesignation;
  }

  @Override
  public int getRoleId() {
    return mRoleId;
  }

  @Override
  public int getStatus() {
    return mStatus;
  }

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return null;
  }
}
