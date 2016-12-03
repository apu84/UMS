package org.ums.domain.model.dto;

import com.google.gson.Gson;

/**
 * Created by Ifti on 30-Nov-16.
 */
public class MarksSubmissionStatDto {

  private String deptName;
  private String deptId;
  private String programName;
  private Integer programId;
  private Integer totalOfferedSelf;
  private Integer totalAcceptedSelf;
  private Integer totalOfferedOthers;
  private Integer totalAcceptedOthers;
  private Integer totalOffered;
  private Integer totalAccepted;

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public String getDeptId() {
    return deptId;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  public String getProgramName() {
    return programName;
  }

  public void setProgramName(String programName) {
    this.programName = programName;
  }

  public Integer getProgramId() {
    return programId;
  }

  public void setProgramId(Integer programId) {
    this.programId = programId;
  }

  public Integer getTotalOfferedSelf() {
    return totalOfferedSelf;
  }

  public void setTotalOfferedSelf(Integer totalOfferedSelf) {
    this.totalOfferedSelf = totalOfferedSelf;
  }

  public Integer getTotalAcceptedSelf() {
    return totalAcceptedSelf;
  }

  public void setTotalAcceptedSelf(Integer totalAcceptedSelf) {
    this.totalAcceptedSelf = totalAcceptedSelf;
  }

  public Integer getTotalOfferedOthers() {
    return totalOfferedOthers;
  }

  public void setTotalOfferedOthers(Integer totalOfferedOthers) {
    this.totalOfferedOthers = totalOfferedOthers;
  }

  public Integer getTotalAcceptedOthers() {
    return totalAcceptedOthers;
  }

  public void setTotalAcceptedOthers(Integer totalAcceptedOthers) {
    this.totalAcceptedOthers = totalAcceptedOthers;
  }

  public Integer getTotalOffered() {
    return totalOffered;
  }

  public void setTotalOffered(Integer totalOffered) {
    this.totalOffered = totalOffered;
  }

  public Integer getTotalAccepted() {
    return totalAccepted;
  }

  public void setTotalAccepted(Integer totalAccepted) {
    this.totalAccepted = totalAccepted;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
