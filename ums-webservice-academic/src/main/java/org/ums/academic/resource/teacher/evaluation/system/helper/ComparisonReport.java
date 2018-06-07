package org.ums.academic.resource.teacher.evaluation.system.helper;

import java.security.PrivateKey;

/**
 * Created by Rumi on 4/2/2018.
 */
public class ComparisonReport {
  private String teacherName;
  private String deptName;
  private String courseNo;
  private String courseTitle;
  private double totalScore;
  private double reviewPercentage;
  private String programName;
  private String teacherId;
  private String courseId;
  private String deptId;

  public ComparisonReport() {

  }

  public ComparisonReport(String teacherName, String deptName, String courseNo, String courseTitle, double totalScore,
      double reviewPercentage, String programName, String teacherId, String courseId, String deptId) {
    this.teacherName = teacherName;
    this.deptName = deptName;
    this.courseNo = courseNo;
    this.courseTitle = courseTitle;
    this.totalScore = totalScore;
    this.reviewPercentage = reviewPercentage;
    this.programName = programName;
    this.teacherId = teacherId;
    this.courseId = courseId;
    this.deptId = deptId;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getDeptId() {
    return deptId;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  public String getProgramName() {
    return programName;
  }

  public void setProgramName(String programName) {
    this.programName = programName;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
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

  public double getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(double totalScore) {
    this.totalScore = totalScore;
  }

  public double getReviewPercentage() {
    return reviewPercentage;
  }

  public void setReviewPercentage(double reviewPercentage) {
    this.reviewPercentage = reviewPercentage;
  }
}
