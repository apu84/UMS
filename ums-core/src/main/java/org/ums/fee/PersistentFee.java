package org.ums.fee;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.ProgramType;
import org.ums.domain.model.immutable.Semester;
import org.ums.manager.FacultyManager;
import org.ums.manager.ProgramTypeManager;
import org.ums.manager.SemesterManager;

public class PersistentFee implements MutableFee {
  private static FeeManager sFeeManager;
  private static FeeCategoryManager sFeeCategoryManager;
  private static SemesterManager sSemesterManager;
  private static ProgramTypeManager sProgramTypeManager;
  private static FacultyManager sFacultyManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sFeeManager = applicationContext.getBean("feeManager", FeeManager.class);
    sFeeCategoryManager =
        applicationContext.getBean("feeCategoryManager", FeeCategoryManager.class);
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sProgramTypeManager =
        applicationContext.getBean("programTypeManager", ProgramTypeManager.class);
    sFacultyManager = applicationContext.getBean("facultyManager", FacultyManager.class);
  }

  private Integer mId;
  private String mFeeCategoryId;
  private FeeCategory mFeeCategory;
  private Integer mSemesterId;
  private Semester mSemester;
  private Integer mFacultyId;
  private Faculty mFaculty;
  private Integer mProgramTypeId;
  private ProgramType mProgramType;
  private Fee.ProgramCategory mProgramCategory;
  private Double mAmount;
  private String mLastModified;

  PersistentFee() {}

  PersistentFee(final PersistentFee pPersistentFee) {
    setId(pPersistentFee.getId());
    setFeeCategoryId(pPersistentFee.getFeeCategoryId());
    setFeeCategory(pPersistentFee.getFeeCategory());
    setSemesterId(pPersistentFee.getSemesterId());
    setSemester(pPersistentFee.getSemester());
    setFacultyId(pPersistentFee.getFacultyId());
    setFaculty(pPersistentFee.getFaculty());
    setProgramTypeId(pPersistentFee.getProgramTypeId());
    setProgramType(pPersistentFee.getProgramType());
    setProgramCategory(pPersistentFee.getProgramCategory());
    setAmount(pPersistentFee.getAmount());
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sFeeManager.update(this);
    }
    else {
      sFeeManager.create(this);
    }
  }

  @Override
  public MutableFee edit() {
    return new PersistentFee(this);
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
  public void delete() {
    sFeeManager.delete(this);
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
    return mFeeCategory == null ? sFeeCategoryManager.get(mFeeCategoryId) : sFeeCategoryManager
        .validate(mFeeCategory);
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
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager
        .validate(mSemester);
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
  public void setProgramTypeId(Integer pProgramTypeId) {
    mProgramTypeId = pProgramTypeId;
  }

  @Override
  public Integer getProgramTypeId() {
    return mProgramTypeId;
  }

  @Override
  public ProgramType getProgramType() {
    return mProgramType == null ? sProgramTypeManager.get(mProgramTypeId) : sProgramTypeManager
        .validate(mProgramType);
  }

  @Override
  public void setProgramType(ProgramType pProgramType) {
    mProgramType = pProgramType;
  }

  @Override
  public ProgramCategory getProgramCategory() {
    return mProgramCategory;
  }

  @Override
  public void setProgramCategory(ProgramCategory pProgramCategory) {
    mProgramCategory = pProgramCategory;
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
