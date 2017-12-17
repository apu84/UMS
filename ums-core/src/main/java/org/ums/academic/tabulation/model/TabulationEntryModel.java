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
}
