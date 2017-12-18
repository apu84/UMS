package org.ums.academic.tabulation.model;

import java.util.List;

import org.ums.domain.model.immutable.Semester;

public class TabulationReportModel {
  private Semester mSemester;
  private int mYear;
  private int mAcademicSemester;
  private List<TabulationEntryModel> mTabulationEntryModels;

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

  public List<TabulationEntryModel> getTabulationEntryModels() {
    return mTabulationEntryModels;
  }

  public void setTabulationEntryModels(List<TabulationEntryModel> pTabulationEntryModels) {
    mTabulationEntryModels = pTabulationEntryModels;
  }
}
