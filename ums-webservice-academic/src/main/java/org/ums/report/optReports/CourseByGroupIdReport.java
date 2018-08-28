package org.ums.report.optReports;

import org.ums.domain.model.immutable.Course;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 8/13/2018.
 */
public class CourseByGroupIdReport {
  public Integer groupId;
  public List<Course> courses;

  public CourseByGroupIdReport(Integer groupId, List<Course> courses) {
    this.groupId = groupId;
    this.courses = courses;
  }

  public CourseByGroupIdReport() {

  }

  public void setGroupId(Integer groupId) {
    this.groupId = groupId;
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }

  public Integer getGroupId() {

    return groupId;
  }

  public List<Course> getCourses() {
    return courses;
  }
}
