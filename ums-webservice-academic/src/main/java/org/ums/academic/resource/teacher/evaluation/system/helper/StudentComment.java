package org.ums.academic.resource.teacher.evaluation.system.helper;

/**
 * Created by Monjur-E-Morshed on 3/14/2018.
 */
public class StudentComment {
  private Long questionId;
  private String questionDetails;
  private String[] comments;
  private Integer observationType;

  public StudentComment() {

  }

  public StudentComment(Long questionId, String[] comments, Integer observationType, String questionDetails) {
    this.questionId = questionId;
    this.comments = comments;
    this.observationType = observationType;
    this.questionDetails = questionDetails;
  }

  public String getQuestionDetails() {
    return questionDetails;
  }

  public void setQuestionDetails(String questionDetails) {
    this.questionDetails = questionDetails;
  }

  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }

  public String[] getComments() {
    return comments;
  }

  public void setComments(String[] comments) {
    this.comments = comments;
  }

  public Integer getObservationType() {
    return observationType;
  }

  public void setObservationType(Integer observationType) {
    this.observationType = observationType;
  }
}
