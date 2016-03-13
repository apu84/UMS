package org.ums.domain.model.dto;

import com.google.gson.Gson;

/**
 * Created by Ifti on 12-Mar-16.
 */
public class OptCourseStudentDto {
  private String studentId;
  private String studentName;
  private String courseId;
  private String appliedOn;
  private String status;

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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

}
