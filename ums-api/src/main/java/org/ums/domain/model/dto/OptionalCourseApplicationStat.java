package org.ums.domain.model.dto;
import java.io.Serializable;

public class OptionalCourseApplicationStat implements Serializable {
  private String courseNumber;
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
}
