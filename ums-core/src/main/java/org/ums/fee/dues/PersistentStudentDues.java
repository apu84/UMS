package org.ums.fee.dues;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Student;
import org.ums.usermanagement.user.User;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeCategoryManager;
import org.ums.manager.StudentManager;
import org.ums.usermanagement.user.UserManager;

public class PersistentStudentDues implements MutableStudentDues {

  private static FeeCategoryManager sFeeCategoryManager;
  private static StudentManager sStudentManager;
  private static UserManager sUserManager;
  private static StudentDuesManager sStudentDuesManager;
  private Long mId;
  private FeeCategory mFeeCategory;
  private String mFeeCategoryId;
  private String mDescription;
  private String mTransactionId;
  private Student mStudent;
  private String mStudentId;
  private BigDecimal mAmount;
  private Date mAddedOn;
  private Date mPayBefore;
  private User mUser;
  private String mUserId;
  private String mLastModified;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  public FeeCategory getFeeCategory() {
    return mFeeCategory == null ? sFeeCategoryManager.get(mFeeCategoryId) : sFeeCategoryManager.validate(mFeeCategory);
  }

  @Override
  public void setFeeCategory(FeeCategory pFeeCategory) {
    this.mFeeCategory = pFeeCategory;
  }

  @Override
  public String getFeeCategoryId() {
    return mFeeCategoryId;
  }

  @Override
  public void setFeeCategoryId(String pFeeCategoryId) {
    this.mFeeCategoryId = pFeeCategoryId;
  }

  @Override
  public String getDescription() {
    return mDescription;
  }

  @Override
  public void setDescription(String pDescription) {
    this.mDescription = pDescription;
  }

  @Override
  public String getTransactionId() {
    return mTransactionId;
  }

  @Override
  public void setTransactionId(String pTransactionId) {
    this.mTransactionId = pTransactionId;
  }

  @Override
  public Student getStudent() {
    return mStudent == null ? sStudentManager.get(mStudentId) : sStudentManager.validate(mStudent);
  }

  @Override
  public void setStudent(Student pStudent) {
    this.mStudent = pStudent;
  }

  @Override
  public String getStudentId() {
    return mStudentId;
  }

  @Override
  public void setStudentId(String pStudentId) {
    this.mStudentId = pStudentId;
  }

  @Override
  public BigDecimal getAmount() {
    return mAmount;
  }

  @Override
  public void setAmount(BigDecimal pAmount) {
    this.mAmount = pAmount;
  }

  @Override
  public Date getAddedOn() {
    return mAddedOn;
  }

  @Override
  public void setAddedOn(Date pAddedOn) {
    this.mAddedOn = pAddedOn;
  }

  @Override
  public Date getPayBefore() {
    return mPayBefore;
  }

  @Override
  public void setPayBefore(Date pPayBefore) {
    this.mPayBefore = pPayBefore;
  }

  @Override
  public User getUser() {
    return mUser == null ? sUserManager.get(mUserId) : sUserManager.validate(mUser);
  }

  @Override
  public void setUser(User pUser) {
    this.mUser = pUser;
  }

  @Override
  public String getUserId() {
    return mUserId;
  }

  @Override
  public void setUserId(String pUserId) {
    this.mUserId = pUserId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.mLastModified = pLastModified;
  }

  @Override
  public Long create() {
    return sStudentDuesManager.create(this);
  }

  @Override
  public void update() {
    sStudentDuesManager.update(this);
  }

  @Override
  public MutableStudentDues edit() {
    return new PersistentStudentDues(this);
  }

  @Override
  public void delete() {
    sStudentDuesManager.delete(this);
  }

  public PersistentStudentDues() {}

  private PersistentStudentDues(MutableStudentDues pStudentDues) {
    setId(pStudentDues.getId());
    setFeeCategory(pStudentDues.getFeeCategory());
    setFeeCategoryId(pStudentDues.getFeeCategoryId());
    setDescription(pStudentDues.getDescription());
    setTransactionId(pStudentDues.getTransactionId());
    setStudent(pStudentDues.getStudent());
    setStudentId(pStudentDues.getStudentId());
    setAmount(pStudentDues.getAmount());
    setAddedOn(pStudentDues.getAddedOn());
    setPayBefore(pStudentDues.getPayBefore());
    setUser(pStudentDues.getUser());
    setUserId(pStudentDues.getUserId());
    setLastModified(pStudentDues.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFeeCategoryManager = applicationContext.getBean("feeCategoryManager", FeeCategoryManager.class);
    sStudentManager = applicationContext.getBean("studentManager", StudentManager.class);
    sUserManager = applicationContext.getBean("userManager", UserManager.class);
    sStudentDuesManager = applicationContext.getBean("studentDuesManager", StudentDuesManager.class);
  }
}
