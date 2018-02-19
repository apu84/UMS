package org.ums.academic.tabulation.model;

import java.util.Map;

import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.UGRegistrationResult;

public class TabulationEntryModel {
  private Student mStudent;
  private Map<String, UGRegistrationResult> mRegularCourseList;
  private Map<String, UGRegistrationResult> mClearanceCourseList;
  private Map<String, UGRegistrationResult> mCarryCourseList;
  private Map<String, UGRegistrationResult> mFailedCoursesCurrentSemesterList;
  private Map<String, UGRegistrationResult> mFailedCoursesPreviousSemesterList;
  private double mGpa;
  private double mCgpa;
  private String mRemarks;
  private double mPresentCompletedCrHr;
  private double mPresentCompletedGradePoints;
  private double mPreviousSemesterCompletedCrHr;
  private double mPreviousSemesterCompletedGradePoints;
  private double mCumulativeCrHr;
  private double mCumulativeGradePoints;

  public Student getStudent() {
    return mStudent;
  }

  public void setStudent(Student pStudent) {
    mStudent = pStudent;
  }

  public Map<String, UGRegistrationResult> getRegularCourseList() {
    return mRegularCourseList;
  }

  public void setRegularCourseList(Map<String, UGRegistrationResult> pRegularCourseList) {
    mRegularCourseList = pRegularCourseList;
  }

  public Map<String, UGRegistrationResult> getClearanceCourseList() {
    return mClearanceCourseList;
  }

  public void setClearanceCourseList(Map<String, UGRegistrationResult> pClearanceCourseList) {
    mClearanceCourseList = pClearanceCourseList;
  }

  public Map<String, UGRegistrationResult> getCarryCourseList() {
    return mCarryCourseList;
  }

  public void setCarryCourseList(Map<String, UGRegistrationResult> pCarryCourseList) {
    mCarryCourseList = pCarryCourseList;
  }

  public Map<String, UGRegistrationResult> getFailedCoursesCurrentSemesterList() {
    return mFailedCoursesCurrentSemesterList;
  }

  public void setFailedCoursesCurrentSemesterList(Map<String, UGRegistrationResult> pFailedCoursesCurrentSemesterList) {
    mFailedCoursesCurrentSemesterList = pFailedCoursesCurrentSemesterList;
  }

  public Map<String, UGRegistrationResult> getFailedCoursesPreviousSemesterList() {
    return mFailedCoursesPreviousSemesterList;
  }

  public void setFailedCoursesPreviousSemesterList(Map<String, UGRegistrationResult> pFailedCoursesPreviousSemesterList) {
    mFailedCoursesPreviousSemesterList = pFailedCoursesPreviousSemesterList;
  }

  public double getGpa() {
    return mGpa;
  }

  public void setGpa(double pGpa) {
    mGpa = pGpa;
  }

  public double getCgpa() {
    return mCgpa;
  }

  public void setCgpa(double pCgpa) {
    mCgpa = pCgpa;
  }

  public String getRemarks() {
    return mRemarks;
  }

  public void setRemarks(String pRemarks) {
    mRemarks = pRemarks;
  }

  public double getPresentCompletedCrHr() {
    return mPresentCompletedCrHr;
  }

  public void setPresentCompletedCrHr(double pPresentCompletedCrHr) {
    mPresentCompletedCrHr = pPresentCompletedCrHr;
  }

  public double getPresentCompletedGradePoints() {
    return mPresentCompletedGradePoints;
  }

  public void setPresentCompletedGradePoints(double pPresentCompletedGradePoints) {
    mPresentCompletedGradePoints = pPresentCompletedGradePoints;
  }

  public double getCumulativeCrHr() {
    return mCumulativeCrHr;
  }

  public void setCumulativeCrHr(double pCumulativeCrHr) {
    mCumulativeCrHr = pCumulativeCrHr;
  }

  public double getCumulativeGradePoints() {
    return mCumulativeGradePoints;
  }

  public void setCumulativeGradePoints(double pCumulativeGradePoints) {
    mCumulativeGradePoints = pCumulativeGradePoints;
  }

  public double getPreviousSemesterCompletedCrHr() {
    return mPreviousSemesterCompletedCrHr;
  }

  public void setPreviousSemesterCompletedCrHr(double pPreviousSemesterCompletedCrHr) {
    mPreviousSemesterCompletedCrHr = pPreviousSemesterCompletedCrHr;
  }

  public double getPreviousSemesterCompletedGradePoints() {
    return mPreviousSemesterCompletedGradePoints;
  }

  public void setPreviousSemesterCompletedGradePoints(double pPreviousSemesterCompletedGradePoints) {
    mPreviousSemesterCompletedGradePoints = pPreviousSemesterCompletedGradePoints;
  }
}
