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

  private Double quiz;
  private Double classPerformance;
  private Double partA;
  private Double partB;
  private Double total;
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

  public Double getQuiz() {
    return quiz;
  }

  public void setQuiz(Double quiz) {
    this.quiz = quiz;
  }

  public Double getClassPerformance() {
    return classPerformance;
  }

  public void setClassPerformance(Double classPerformance) {
    this.classPerformance = classPerformance;
  }

  public Double getPartA() {
    return partA;
  }

  public void setPartA(Double partA) {
    this.partA = partA;
  }

  public Double getPartB() {
    return partB;
  }

  public void setPartB(Double partB) {
    this.partB = partB;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
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
