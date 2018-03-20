package org.ums.domain.model.dto;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Ifti on 29-Oct-16.
 */
public class ClassAttendanceDto {

  private String id;
  private String classDate;
  private String classDateFormat1;
  private Integer serial;
  private String section;
  private String teacherId;
  private String teacherName;
  private String teacherShortName;
  private String studentId;
  private String studentName;
  private Integer attendance;
  private String courseId;
  private Integer semesterId;
  private List<String> attendances;

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

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getAttendance() {
    return attendance;
  }

  public void setAttendance(Integer attendance) {
    this.attendance = attendance;
  }

  public List<String> getAttendances() {
    return attendances;
  }

  public void setAttendances(List<String> attendances) {
    this.attendances = attendances;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getTeacherShortName() {
    return teacherShortName;
  }

  public void setTeacherShortName(String teacherShortName) {
    this.teacherShortName = teacherShortName;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public Integer getSemesterId() {
    return semesterId;
  }

  public void setSemesterId(Integer semesterId) {
    this.semesterId = semesterId;
  }

  public String getSection() {
    return section;
  }

  public void setSection(String section) {
    this.section = section;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
