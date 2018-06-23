package org.ums.academic.resource.exam.attendant.helper;

/**
 * Created by Monjur-E-Morshed on 6/12/2018.
 */
public class ExamAttendantYearSemesterWiseData {
  private Integer year;
  private Integer semester;
  private Integer totalAttendantStudentNumber;
  private Integer programId;
  private String courseId;
  private Integer absentStudent;
  private Integer presentStudent;

  public ExamAttendantYearSemesterWiseData(Integer year, Integer semester, Integer totalAttendantStudentNumber,
      Integer programId, String courseId, Integer absentStudent, Integer presentStudent) {
    this.year = year;
    this.semester = semester;
    this.totalAttendantStudentNumber = totalAttendantStudentNumber;
    this.programId = programId;
    this.courseId = courseId;
    this.absentStudent = absentStudent;
    this.presentStudent = presentStudent;
  }

  public Integer getAbsentStudent() {
    return absentStudent;
  }

  public void setAbsentStudent(Integer absentStudent) {
    this.absentStudent = absentStudent;
  }

  public Integer getPresentStudent() {
    return presentStudent;
  }

  public void setPresentStudent(Integer presentStudent) {
    this.presentStudent = presentStudent;
  }

  public ExamAttendantYearSemesterWiseData() {

  }

  public Integer getProgramId() {
    return programId;
  }

  public void setProgramId(Integer programId) {
    this.programId = programId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getSemester() {
    return semester;
  }

  public void setSemester(Integer semester) {
    this.semester = semester;
  }

  public Integer getTotalAttendantStudentNumber() {
    return totalAttendantStudentNumber;
  }

  public void setTotalAttendantStudentNumber(Integer totalAttendantStudentNumber) {
    this.totalAttendantStudentNumber = totalAttendantStudentNumber;
  }
}
