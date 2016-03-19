package org.ums.domain.model.dto;

import com.google.gson.Gson;
import org.ums.domain.model.immutable.Student;

import java.util.List;

/**
 * Created by Ifti on 16-Mar-16.
 */
public class OptSectionDto {
  private String courseId;
  private String courseNumber;
  private String courseTitle;
  private String sectionName;
  private String students;
  private List<Student> studentList;

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

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  public List<Student> getStudentList() {
    return studentList;
  }
  public void setStudentList(List<Student> studentList) {
    this.studentList = studentList;
  }
  public String getStudents() {
    return students;
  }

  public void setStudents(String students) {
    this.students = students;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
