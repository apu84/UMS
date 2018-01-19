package org.ums.academic.tabulation.model;

import java.util.List;

import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Semester;

public class TabulationReportModel {
  private Semester mSemester;
  private int mYear;
  private int mAcademicSemester;
  private List<TabulationEntryModel> mTabulationEntries;
  private List<Course> mTheoryCourses;
  private List<Course> mSessionalCourses;
  private Department mDepartment;

  public Semester getSemester() {
    return mSemester;
  }

  public void setSemester(Semester pSemester) {
    mSemester = pSemester;
  }

  public int getYear() {
    return mYear;
  }

  public void setYear(int pYear) {
    mYear = pYear;
  }

  public int getAcademicSemester() {
    return mAcademicSemester;
  }

  public void setAcademicSemester(int pAcademicSemester) {
    mAcademicSemester = pAcademicSemester;
  }

  public List<TabulationEntryModel> getTabulationEntries() {
    return mTabulationEntries;
  }

  public void setTabulationEntries(List<TabulationEntryModel> pTabulationEntries) {
    mTabulationEntries = pTabulationEntries;
  }

  public List<Course> getTheoryCourses() {
    return mTheoryCourses;
  }

  public void setTheoryCourses(List<Course> pTheoryCourses) {
    mTheoryCourses = pTheoryCourses;
  }

  public List<Course> getSessionalCourses() {
    return mSessionalCourses;
  }

  public void setSessionalCourses(List<Course> pSessionalCourses) {
    mSessionalCourses = pSessionalCourses;
  }

  public Department getDepartment() {
    return mDepartment;
  }

  public void setDepartment(Department pDepartment) {
    mDepartment = pDepartment;
  }
}
