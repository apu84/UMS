package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableSubGroup;
import org.ums.manager.SeatPlanGroupManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.SubGroupManager;

/**
 * Created by My Pc on 5/4/2016.
 */
public class PersistentSubGroup implements MutableSubGroup {

  private static SemesterManager sSemesterManager;
  private static SeatPlanGroupManager sSeatPlanGroupManager;
  private static SubGroupManager sSubGroupManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSemesterManager = applicationContext.getBean("semesterManager", SemesterManager.class);
    sSeatPlanGroupManager = applicationContext.getBean("seatPlanGroupManager", SeatPlanGroupManager.class);
    sSubGroupManager = applicationContext.getBean("subGroupManager", SubGroupManager.class);
  }

  private int mId;
  private Semester mSemester;
  private int mSemesterId;
  private SeatPlanGroup mSeatPlanGroup;
  private int mSubGroupNo;
  private int mGroupNo;
  private int mGroupId;
  private int mPosition;
  private int mStudentNumber;
  private String mLastModified;
  private int mExamType;
  private String mProgramShortName;
  private Integer mStudentYear;
  private Integer mStudentSemester;

  public PersistentSubGroup() {

  }

  public PersistentSubGroup(final PersistentSubGroup pPersistentSubGroup) {
    mId = pPersistentSubGroup.getId();
    mSemester = pPersistentSubGroup.getSemester();
    mSemesterId = pPersistentSubGroup.getSemesterId();
    mSeatPlanGroup = pPersistentSubGroup.getGroup();
    mGroupNo = pPersistentSubGroup.getGroupNo();
    mGroupId = pPersistentSubGroup.getGroupId();
    mSubGroupNo = pPersistentSubGroup.subGroupNo();
    mPosition = pPersistentSubGroup.getPosition();
    mStudentNumber = pPersistentSubGroup.getStudentNumber();
    mLastModified = pPersistentSubGroup.getLastModified();
    mExamType = pPersistentSubGroup.getExamType();
  }

  @Override
  public void setProgramShortName(String pProgramShortName) {
    mProgramShortName = pProgramShortName;
  }

  @Override
  public void setStudentYear(Integer pStudentYear) {
    mStudentYear = pStudentYear;
  }

  @Override
  public void setStudentSemester(Integer pStudentSemester) {
    mStudentSemester = pStudentSemester;
  }

  @Override
  public String getProgramShortName() {
    return mProgramShortName;
  }

  @Override
  public Integer getStudentYear() {
    return mStudentYear;
  }

  @Override
  public Integer getStudentSemester() {
    return mStudentSemester;
  }

  @Override
  public void setExamType(int pExamType) {
    mExamType = pExamType;
  }

  @Override
  public int getExamType() {
    return mExamType;
  }

  @Override
  public void setStudentNumber(int pStudentNumber) {
    mStudentNumber = pStudentNumber;
  }

  @Override
  public int getStudentNumber() {
    return mStudentNumber;
  }

  @Override
  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  @Override
  public void setGroup(SeatPlanGroup pGroup) {
    mSeatPlanGroup = pGroup;
  }

  @Override
  public void setSubGroupNo(int pSubGroupNo) {
    mSubGroupNo = pSubGroupNo;
  }

  @Override
  public void setPosition(int pPosition) {
    mPosition = pPosition;
  }

  @Override
  public Semester getSemester() {
    return mSemester == null ? sSemesterManager.get(mSemesterId) : sSemesterManager.validate(mSemester);
  }

  @Override
  public MutableSubGroup edit() {
    return new PersistentSubGroup(this);
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sSubGroupManager.update(this);
    }
    else {
      sSubGroupManager.create(this);
    }
  }

  @Override
  public void delete() {
    sSubGroupManager.delete(this);
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
  public SeatPlanGroup getGroup() {
    return mSeatPlanGroup == null ? sSeatPlanGroupManager.get(mGroupId) : sSeatPlanGroupManager
        .validate(mSeatPlanGroup);
  }

  @Override
  public int subGroupNo() {
    return mSubGroupNo;
  }

  @Override
  public int getPosition() {
    return mPosition;
  }

  public int getSemesterId() {
    return mSemesterId;
  }

  public void setSemesterId(int pSemesterId) {
    mSemesterId = pSemesterId;
  }

  public int getGroupNo() {
    return mGroupNo;
  }

  public void setGroupNo(int pGroupNo) {
    mGroupNo = pGroupNo;
  }

  public int getGroupId() {
    return mGroupId;
  }

  public void setGroupId(int pGroupId) {
    mGroupId = pGroupId;
  }
}
