package org.ums.domain.model.dto;

import java.io.Serializable;

/**
 * Created by My Pc on 20-Aug-16.
 */
public class SeatPlanReportDto implements Serializable {
  private String mRoomNo;
  private String mProgramName;
  private String mCourseTitle;
  private String mCourseNo;
  private String mExamDate;
  private Integer mCurrentYear;
  private Integer mCurrentSemester;
  private String mStudentId;

  public SeatPlanReportDto() {

  }

  public String getRoomNo() {
    return mRoomNo;
  }

  public void setRoomNo(String pRoomNo) {
    mRoomNo = pRoomNo;
  }

  public String getProgramName() {
    return mProgramName;
  }

  public void setProgramName(String pProgramShortName) {
    mProgramName = pProgramShortName;
  }

  public String getCourseTitle() {
    return mCourseTitle;
  }

  public void setCourseTitle(String pCourseTitle) {
    mCourseTitle = pCourseTitle;
  }

  public String getCourseNo() {
    return mCourseNo;
  }

  public void setCourseNo(String pCourseNo) {
    mCourseNo = pCourseNo;
  }

  public String getExamDate() {
    return mExamDate;
  }

  public void setExamDate(String pExamDate) {
    mExamDate = pExamDate;
  }

  public Integer getCurrentYear() {
    return mCurrentYear;
  }

  public void setCurrentYear(Integer pCurrentYear) {
    mCurrentYear = pCurrentYear;
  }

  public Integer getCurrentSemester() {
    return mCurrentSemester;
  }

  public void setCurrentSemester(Integer pCurrentSemester) {
    mCurrentSemester = pCurrentSemester;
  }

  public String getStudentId() {
    return mStudentId;
  }

  public void setStudentId(String pStudentId) {
    mStudentId = pStudentId;
  }
}
