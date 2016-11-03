package org.ums.domain.model.dto;

import com.google.gson.Gson;

/**
 * Created by Ifti on 29-Oct-16.
 */
public class ClassAttendanceDto {

  private String id;
  private String classDate;
  private String classDateFormat1;
  private Integer serial;
  private String teacherId;
  private String studentId;
  private String studentName;
  private Integer attendance;

  public String getClassDate() {
    return classDate;
  }

  public void setClassDate(String classDate) {
    this.classDate = classDate;
  }

  public String getClassDateFormat1() {
    return classDateFormat1;
  }

  public void setClassDateFormat1(String classDateFormat1) {
    this.classDateFormat1 = classDateFormat1;
  }

  public Integer getSerial() {
    return serial;
  }

  public void setSerial(Integer serial) {
    this.serial = serial;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public String getStudentName() {
    return studentName;
  }

  public Integer getAttendance() {
    return attendance;
  }

  public void setAttendance(Integer attendance) {
    this.attendance = attendance;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
