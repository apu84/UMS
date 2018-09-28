package org.ums.services.academic.routine.helper;

import org.ums.domain.model.immutable.routine.Routine;

/**
 * Created by Monjur-E-Morshed on 27-Sep-18.
 */
public class RoutineErrorLog {
  private int year;
  private int semester;
  private String section;
  private String errorMessage;
  private String startTime;
  private String endTime;
  private int day;

  public RoutineErrorLog() {}

  public RoutineErrorLog(int pYear, int pSemester, String pSection, String pErrorMessage, String pStartTime,
      String pEndTime, int pDay) {
    year = pYear;
    semester = pSemester;
    section = pSection;
    errorMessage = pErrorMessage;
    startTime = pStartTime;
    endTime = pEndTime;
    day = pDay;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int pYear) {
    year = pYear;
  }

  public int getSemester() {
    return semester;
  }

  public void setSemester(int pSemester) {
    semester = pSemester;
  }

  public String getSection() {
    return section;
  }

  public void setSection(String pSection) {
    section = pSection;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String pErrorMessage) {
    errorMessage = pErrorMessage;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String pStartTime) {
    startTime = pStartTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String pEndTime) {
    endTime = pEndTime;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int pDay) {
    day = pDay;
  }
}
