package org.ums.report.optReports;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.library.Publisher;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 8/13/2018.
 */
public class CourseByGroupIdReport {
  public Integer groupId;
  public String groupName;
  public List<Course> courses;

  public CourseByGroupIdReport(Integer groupId, String groupName, List<Course> courses) {
    this.groupId = groupId;
    this.groupName = groupName;
    this.courses = courses;
  }

  public CourseByGroupIdReport() {

  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
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
