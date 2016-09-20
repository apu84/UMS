package org.ums.domain.model.dto;


import com.google.gson.Gson;
import org.ums.enums.CourseMarksSubmissionStatus;

public class MarksSubmissionStatusLogDto {
  private String userId;
  private String userName;
  private String roleName;
  private String insertedOn;
  private CourseMarksSubmissionStatus status;
  private String statusName;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getInsertedOn() {
    return insertedOn;
  }

  public void setInsertedOn(String insertedOn) {
    this.insertedOn = insertedOn;
  }

  public CourseMarksSubmissionStatus getStatus() {
    return status;
  }

  public void setStatus(CourseMarksSubmissionStatus status) {
    this.status = status;
  }

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

}
