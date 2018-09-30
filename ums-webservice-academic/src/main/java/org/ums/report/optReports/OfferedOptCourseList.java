package org.ums.report.optReports;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 9/20/2018.
 */
public class OfferedOptCourseList {
  public Long groupId;
  public String groupName;
  public Integer programId;
  public Integer totalApplied;
  public Integer totalSeats;
  public Integer year;
  public Integer semester;
  public Integer choice;
  public Boolean isMandatory;
  public List<CourseList> courses;
  public List<SubGroupList> subGrpCourses;

  public OfferedOptCourseList() {}

  public OfferedOptCourseList(Long groupId, String groupName, Integer programId, Integer totalApplied,
      Integer totalSeats, Integer year, Integer semester, Integer choice, Boolean isMandatory,
      List<CourseList> courses, List<SubGroupList> subGroupCourses) {
    this.groupId = groupId;
    this.groupName = groupName;
    this.programId = programId;
    this.totalApplied = totalApplied;
    this.totalSeats = totalSeats;
    this.year = year;
    this.semester = semester;
    this.choice = choice;
    this.isMandatory = isMandatory;
    this.courses = courses;
    this.subGrpCourses = subGroupCourses;
  }

  public Integer getChoice() {
    return choice;
  }

  public void setChoice(Integer choice) {
    this.choice = choice;
  }

  public Boolean getMandatory() {
    return isMandatory;
  }

  public void setMandatory(Boolean mandatory) {
    isMandatory = mandatory;
  }

  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public Integer getProgramId() {
    return programId;
  }

  public void setProgramId(Integer programId) {
    this.programId = programId;
  }

  public Integer getTotalApplied() {
    return totalApplied;
  }

  public void setTotalApplied(Integer totalApplied) {
    this.totalApplied = totalApplied;
  }

  public Integer getTotalSeats() {
    return totalSeats;
  }

  public void setTotalSeats(Integer totalSeats) {
    this.totalSeats = totalSeats;
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

  public List<CourseList> getCourses() {
    return courses;
  }

  public void setCourses(List<CourseList> courses) {
    this.courses = courses;
  }

  public List<SubGroupList> getSubGrpCourses() {
    return subGrpCourses;
  }

  public void setSubGrpCourses(List<SubGroupList> subGrpCourses) {
    this.subGrpCourses = subGrpCourses;
  }
}
