package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.ClassRoom;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.SpStudent;
import org.ums.domain.model.mutable.MutableSeatPlan;
import org.ums.domain.model.mutable.MutableSeatPlanReport;
import org.ums.manager.SeatPlanReportManager;

/**
 * Created by My Pc on 20-Aug-16.
 */
public class PersistentSeatPlanReport implements MutableSeatPlanReport {

  private static SeatPlanReportManager sSeatPlanReportManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSeatPlanReportManager = applicationContext.getBean("seatPlanReportManager", SeatPlanReportManager.class);
  }

  private String mRoomNo;
  private String mProgramShortName;
  private String mCourseTitle;
  private String mCourseNo;
  private String mExamDate;
  private Integer mCurrentYear;
  private Integer mCurrentSemester;
  private String mStudentId;

  public PersistentSeatPlanReport() {

  }

  @Override
  public void setRoomNo(String pRoomNo) {
    mRoomNo = pRoomNo;
  }

  @Override
  public void setProgramName(String pProgramShortName) {
    mProgramShortName = pProgramShortName;
  }

  @Override
  public void setCourseTitle(String pCourseTitle) {
    mCourseTitle = pCourseTitle;
  }

  @Override
  public void setCourseNo(String pCourseNo) {
    mCourseNo = pCourseNo;
  }

  @Override
  public void setCurrentYear(Integer pCurrentYear) {
    mCurrentYear = pCurrentYear;
  }

  @Override
  public void setCurrentSemester(Integer pCurrentSemester) {
    mCurrentSemester = pCurrentSemester;
  }

  @Override
  public void setStudentId(String pStudentId) {
    mStudentId = pStudentId;
  }

  @Override
  public Integer create() {
    return null;
  }

  @Override
  public void update() {}

  @Override
  public void delete() {}

  @Override
  public String getRoomNo() {
    return mRoomNo;
  }

  @Override
  public String getProgramName() {
    return mProgramShortName;
  }

  @Override
  public String getCourseTitle() {
    return mCourseTitle;
  }

  @Override
  public String getCourseNo() {
    return mCourseNo;
  }

  @Override
  public String getExamDate() {
    return mExamDate;
  }

  @Override
  public Integer getCurrentYear() {
    return mCurrentYear;
  }

  @Override
  public Integer getCurrentSemester() {
    return mCurrentSemester;
  }

  @Override
  public String getStudentId() {
    return mStudentId;
  }

  @Override
  public MutableSeatPlanReport edit() {
    return null;
  }
}
