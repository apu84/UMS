package org.ums.domain.model.dto;

import com.google.gson.Gson;

import java.io.Serializable;

public class ExamRoutineDto implements Serializable {

  private String examDate;
  private String examTime;
  private String courseId;
  private String courseNumber;
  private String courseTitle;
  private int courseYear;
  private int courseSemester;
  private int programId;
  private String programName;


  public String getExamDate() {
    return examDate;
  }

  public void setExamDate(String examDate) {
    this.examDate = examDate;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getCourseNumber() {
    return courseNumber;
  }

  public void setCourseNumber(String courseNumber) {
    this.courseNumber = courseNumber;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public int getCourseYear() {
    return courseYear;
  }

  public void setCourseYear(int courseYear) {
    this.courseYear = courseYear;
  }

  public int getCourseSemester() {
    return courseSemester;
  }

  public void setCourseSemester(int courseSemester) {
    this.courseSemester = courseSemester;
  }

  public int getProgramId() {
    return programId;
  }

  public void setProgramId(int programId) {
    this.programId = programId;
  }

  public String getProgramName() {
    return programName;
  }

  public void setProgramName(String programName) {
    this.programName = programName;
  }

  public String getExamTime() {
    return examTime;
  }

  public void setExamTime(String examTime) {
    this.examTime = examTime;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
