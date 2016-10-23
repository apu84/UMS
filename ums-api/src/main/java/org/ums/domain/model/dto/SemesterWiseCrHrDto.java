package org.ums.domain.model.dto;

import com.google.gson.Gson;

/**
 * Created by Ifti on 26-Mar-16.
 */
public class SemesterWiseCrHrDto {

  private String syllabus_id;
  private Integer year;
  private Integer semester;
  private Float totalCrHr;
  private Float theoryChHr;
  private Float sessionalCrHr;
  private Float optionalCrHr;
  private Float optionalTheoryCrHr;
  private Float optionalSessionalCrHr;

  public String getSyllabus_id() {
    return syllabus_id;
  }

  public void setSyllabus_id(String syllabus_id) {
    this.syllabus_id = syllabus_id;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getSemester() {
    return semester;
  }

  public void setSemester(Integer semester) {
    this.semester = semester;
  }

  public Float getTotalCrHr() {
    return totalCrHr;
  }

  public void setTotalCrHr(Float totalCrHr) {
    this.totalCrHr = totalCrHr;
  }

  public Float getTheoryChHr() {
    return theoryChHr;
  }

  public void setTheoryChHr(Float theoryChHr) {
    this.theoryChHr = theoryChHr;
  }

  public Float getSessionalCrHr() {
    return sessionalCrHr;
  }

  public void setSessionalCrHr(Float sessionalCrHr) {
    this.sessionalCrHr = sessionalCrHr;
  }

  public Float getOptionalCrHr() {
    return optionalCrHr;
  }

  public void setOptionalCrHr(Float optionalCrHr) {
    this.optionalCrHr = optionalCrHr;
  }

  public Float getOptionalTheoryCrHr() {
    return optionalTheoryCrHr;
  }

  public void setOptionalTheoryCrHr(Float optionalTheoryCrHr) {
    this.optionalTheoryCrHr = optionalTheoryCrHr;
  }

  public Float getOptionalSessionalCrHr() {
    return optionalSessionalCrHr;
  }

  public void setOptionalSessionalCrHr(Float optionalSessionalCrHr) {
    this.optionalSessionalCrHr = optionalSessionalCrHr;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

}
