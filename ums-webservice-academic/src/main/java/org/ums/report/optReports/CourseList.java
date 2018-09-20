package org.ums.report.optReports;

/**
 * Created by Monjur-E-Morshed on 9/20/2018.
 */
public class CourseList {
  private String Id;
  private String title;
  private String no;
  private Integer crHr;
  private String courseType;
  private Integer year;
  private Integer semester;
  private String pairCourseId;
  private Integer statusId;

  public CourseList() {

  }

  public CourseList(String id, String title, String no, Integer crHr, String courseType, Integer year,
      Integer semester, String pairCourseId, Integer statusId) {
    Id = id;
    this.title = title;
    this.no = no;
    this.crHr = crHr;
    this.courseType = courseType;
    this.year = year;
    this.semester = semester;
    this.pairCourseId = pairCourseId;
    this.statusId = statusId;
  }

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getNo() {
    return no;
  }

  public void setNo(String no) {
    this.no = no;
  }

  public Integer getCrHr() {
    return crHr;
  }

  public void setCrHr(Integer crHr) {
    this.crHr = crHr;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
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

  public String getPairCourseId() {
    return pairCourseId;
  }

  public void setPairCourseId(String pairCourseId) {
    this.pairCourseId = pairCourseId;
  }

  public Integer getStatusId() {
    return statusId;
  }

  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }
}
