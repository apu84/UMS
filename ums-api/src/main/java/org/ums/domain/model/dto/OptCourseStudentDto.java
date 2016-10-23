package org.ums.domain.model.dto;

import com.google.gson.Gson;

/**
 * Created by Ifti on 12-Mar-16.
 */
public class OptCourseStudentDto {
  private String studentId;
  private String studentName;
  private String courseId;
  private String courseNo;
  private String courseTitle;
  private String appliedOn;
  private int statusId;
  private String statusLabel;
  private int applicationTypeId;
  private String applicationTypeLabel;

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getAppliedOn() {
    return appliedOn;
  }

  public void setAppliedOn(String appliedOn) {
    this.appliedOn = appliedOn;
  }

  public String getCourseNo() {
    return courseNo;
  }

  public void setCourseNo(String courseNo) {
    this.courseNo = courseNo;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public int getStatusId() {
    return statusId;
  }

  public void setStatusId(int statusId) {
    this.statusId = statusId;
  }

  public String getStatusLabel() {
    return statusLabel;
  }

  public void setStatusLabel(String statusLabel) {
    this.statusLabel = statusLabel;
  }

  public int getApplicationTypeId() {
    return applicationTypeId;
  }

  public void setApplicationTypeId(int applicationTypeId) {
    this.applicationTypeId = applicationTypeId;
  }

  public String getApplicationTypeLabel() {
    return applicationTypeLabel;
  }

  public void setApplicationTypeLabel(String applicationTypeLabel) {
    this.applicationTypeLabel = applicationTypeLabel;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

}
