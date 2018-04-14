package org.ums.academic.resource.teacher.evaluation.system.helper;

/**
 * Created by Monjur-E-Morshed on 4/14/2018.
 */
public class QuestionWiseReport {
  private String teacherName;
  private String courseNo;
  private String courseTitle;
  private String programName;
  private double score;
  private double percentage;

  public QuestionWiseReport() {

  }

  public QuestionWiseReport(String teacherName, String courseNo, String courseTitle, String programName, double score,
      double percentage) {
    this.teacherName = teacherName;
    this.courseNo = courseNo;
    this.courseTitle = courseTitle;
    this.programName = programName;
    this.score = score;
    this.percentage = percentage;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
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

  public String getProgramName() {
    return programName;
  }

  public void setProgramName(String programName) {
    this.programName = programName;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

  public double getPercentage() {
    return percentage;
  }

  public void setPercentage(double percentage) {
    this.percentage = percentage;
  }
}
