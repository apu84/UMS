package org.ums.academic.resource.exam.attendant.helper;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 6/12/2018.
 */
public class StudentsExamAttendantData {
  private String programName;
  private List<ExamAttendantYearSemesterWiseData> examAttendantYearSemesterWiseDataList;

  public StudentsExamAttendantData() {}

  public StudentsExamAttendantData(String programName,
      List<ExamAttendantYearSemesterWiseData> examAttendantYearSemesterWiseDataList) {
    this.programName = programName;
    this.examAttendantYearSemesterWiseDataList = examAttendantYearSemesterWiseDataList;
  }

  public String getProgramName() {
    return programName;
  }

  public void setProgramName(String programName) {
    this.programName = programName;
  }

  public List<ExamAttendantYearSemesterWiseData> getExamAttendantYearSemesterWiseDataList() {
    return examAttendantYearSemesterWiseDataList;
  }

  public void setExamAttendantYearSemesterWiseDataList(
      List<ExamAttendantYearSemesterWiseData> examAttendantYearSemesterWiseDataList) {
    this.examAttendantYearSemesterWiseDataList = examAttendantYearSemesterWiseDataList;
  }
}
