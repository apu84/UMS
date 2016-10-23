package org.ums.domain.model.dto;

import com.google.gson.Gson;

import java.io.Serializable;

public class OptionalCourseApplicationStatDto implements Serializable {
  private String courseNumber;
  private String courseTitle;
  private Integer totalApplied;

  public String getCourseNumber() {
    return courseNumber;
  }

  public void setCourseNumber(String courseNumber) {
    this.courseNumber = courseNumber;
  }

  public Integer getTotalApplied() {
    return totalApplied;
  }

  public void setTotalApplied(Integer totalApplied) {
    this.totalApplied = totalApplied;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

}
