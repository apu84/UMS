package org.ums.domain.model.dto;

import com.google.gson.Gson;
import org.ums.enums.CourseMarksSubmissionStatus;

public class MarksLogDto {

  private String userId;
  private String userName;
  private String roleName;
  private String insertedOn;
  private CourseMarksSubmissionStatus status;
  private String statusName;

  private Float quiz;
  private Float classPerformance;
  private Float partA;
  private Float partB;
  private Float total;
  private String gradeLetter;
  private int recheckStatus;

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

  public Float getQuiz() {
    return quiz;
  }

  public void setQuiz(Float quiz) {
    this.quiz = quiz;
  }

  public Float getClassPerformance() {
    return classPerformance;
  }

  public void setClassPerformance(Float classPerformance) {
    this.classPerformance = classPerformance;
  }

  public Float getPartA() {
    return partA;
  }

  public void setPartA(Float partA) {
    this.partA = partA;
  }

  public Float getPartB() {
    return partB;
  }

  public void setPartB(Float partB) {
    this.partB = partB;
  }

  public Float getTotal() {
    return total;
  }

  public void setTotal(Float total) {
    this.total = total;
  }

  public String getGradeLetter() {
    return gradeLetter;
  }

  public void setGradeLetter(String gradeLetter) {
    this.gradeLetter = gradeLetter;
  }

  public int getRecheckStatus() {
    return recheckStatus;
  }

  public void setRecheckStatus(int recheckStatus) {
    this.recheckStatus = recheckStatus;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

}
