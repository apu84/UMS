package org.ums.domain.model.dto;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;

public class ExamRoutineDto implements Serializable {

  private String examDate;
  private String examTime;
  private String courseId;
  private String courseNumber;
  private String courseTitle;
  private Integer courseYear;
  private Integer courseSemester;
  private Integer programId;
  private String programName;
  private Integer totalStudent;
  private String examDateOriginal;
  private Integer examGroup;
  private String appDeadLineStr;
  private Date appDeadLine;

  public String getExamDateOriginal() {
    return examDateOriginal;
  }

  public void setExamDateOriginal(String pExamDateOriginal) {
    examDateOriginal = pExamDateOriginal;
  }

  public Integer getTotalStudent() {
    return totalStudent;
  }

  public void setTotalStudent(Integer pTotalStudent) {
    totalStudent = pTotalStudent;
  }

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

  public Integer getCourseYear() {
    return courseYear;
  }

  public void setCourseYear(Integer courseYear) {
    this.courseYear = courseYear;
  }

  public Integer getCourseSemester() {
    return courseSemester;
  }

  public void setCourseSemester(Integer courseSemester) {
    this.courseSemester = courseSemester;
  }

  public Integer getProgramId() {
    return programId;
  }

  public void setProgramId(Integer programId) {
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

  public Integer getExamGroup() {
    return examGroup;
  }

  public void setExamGroup(Integer examGroup) {
    this.examGroup = examGroup;
  }

  public String getAppDeadLineStr() {
    return appDeadLineStr;
  }

  public void setAppDeadLineStr(String appDeadLineStr) {
    this.appDeadLineStr = appDeadLineStr;
  }

  public Date getAppDeadLine() {
    return appDeadLine;
  }

  public void setAppDeadLine(Date appDeadLine) {
    this.appDeadLine = appDeadLine;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
