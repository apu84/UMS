package org.ums.fee;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.Semester;
import org.ums.manager.FacultyManager;
import org.ums.manager.SemesterManager;

public class PersistentUGFee implements MutableUGFee {
  private static UGFeeManager sUGFeeManager;
  private static FeeCategoryManager sFeeCategoryManager;
  private static SemesterManager sSemesterManager;
  private static FacultyManager sFacultyManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sUGFeeManager = applicationContext.getBean("feeManager", UGFeeManager.class);
    sFeeCategoryManager = applicationContext.getBean("feeCategoryManager", FeeCategoryManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sFacultyManager = applicationContext.getBean("facultyManager", FacultyManager.class);
  }

  private Long mId;
  private String mFeeCategoryId;
  private FeeCategory mFeeCategory;
  private Integer mSemesterId;
  private Semester mSemester;
  private Integer mFacultyId;
  private Faculty mFaculty;
  private Double mAmount;
  private String mLastModified;

  PersistentUGFee() {}

  PersistentUGFee(final PersistentUGFee pPersistentFee) {
    setId(pPersistentFee.getId());
    setFeeCategoryId(pPersistentFee.getFeeCategoryId());
    setFeeCategory(pPersistentFee.getFeeCategory());
    setSemesterId(pPersistentFee.getSemesterId());
    setSemester(pPersistentFee.getSemester());
    setFacultyId(pPersistentFee.getFacultyId());
    setFaculty(pPersistentFee.getFaculty());
    setAmount(pPersistentFee.getAmount());
  }

  @Override
  public Long create() {
    return sUGFeeManager.create(this);
  }

  @Override
  public void update() {
    sUGFeeManager.update(this);
  }

  @Override
  public MutableUGFee edit() {
    return new PersistentUGFee(this);
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
    sUGFeeManager.delete(this);
  }

  @Override
  public void setFeeCategoryId(String pFeeCategoryId) {
    mFeeCategoryId = pFeeCategoryId;
  }

  @Override
  public void setFeeCategory(FeeCategory pFeeCategory) {
    mFeeCategory = pFeeCategory;
  }

  @Override
  public String getFeeCategoryId() {
    return mFeeCategoryId;
  }

  @Override
  public void setSemesterId(Integer pSemesterId) {
    mSemesterId = pSemesterId;
  }

  @Override
  public FeeCategory getFeeCategory() {
    return mFeeCategory == null ? sFeeCategoryManager.get(mFeeCategoryId) : sFeeCategoryManager.validate(mFeeCategory);
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public Integer getSemesterId() {
    return mSemesterId;
  }

  @Override
  public void setFacultyId(Integer pFacultyId) {
    mFacultyId = pFacultyId;
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public Integer getFacultyId() {
    return mFacultyId;
  }

  @Override
  public void setFaculty(Faculty pFaculty) {
    mFaculty = pFaculty;
  }

  @Override
  public Faculty getFaculty() {
    return mFaculty == null ? sFacultyManager.get(mFacultyId) : sFacultyManager.validate(mFaculty);
  }

  @Override
  public Double getAmount() {
    return mAmount;
  }

  @Override
  public void setAmount(Double pAmount) {
    mAmount = pAmount;
  }
}
