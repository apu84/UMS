package org.ums.academic.resource.teacher.evaluation.system.helper;

/**
 * Created by Monjur-E-Morshed on 3/13/2018.
 */
public class Report {
  private Integer questionId;
  private String questionDetails;
  private double totalScore;
  private Integer studentNo;
  private double averageScore;
  private Integer observationType;
  private double cRovservation;

  public Report() {

  }

  public Report(double cRovservation) {
    this.cRovservation = cRovservation;
  }

  public Report(Integer questionId, String questionDetails, double totalScore, Integer studentNo, double averageScore,
      Integer observationType) {
    this.questionId = questionId;
    this.questionDetails = questionDetails;
    this.totalScore = totalScore;
    this.studentNo = studentNo;
    this.averageScore = averageScore;
    this.observationType = observationType;
  }

  public double getcRovservation() {
    return cRovservation;
  }

  public void setcRovservation(double cRovservation) {
    this.cRovservation = cRovservation;
  }

  public Integer getObservationType() {
    return observationType;
  }

  public void setObservationType(Integer observationType) {
    this.observationType = observationType;
  }

  public double getAverageScore() {
    return averageScore;
  }

  public void setAverageScore(double averageScore) {
    this.averageScore = averageScore;
  }

  public Integer getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Integer questionId) {
    this.questionId = questionId;
  }

  public String getQuestionDetails() {
    return questionDetails;
  }

  public void setQuestionDetails(String questionDetails) {
    this.questionDetails = questionDetails;
  }

  public double getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(double totalScore) {
    this.totalScore = totalScore;
  }

  public Integer getStudentNo() {
    return studentNo;
  }

  public void setStudentNo(Integer studentNo) {
    this.studentNo = studentNo;
  }
}
