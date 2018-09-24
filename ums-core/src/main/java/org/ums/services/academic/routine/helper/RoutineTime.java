package org.ums.services.academic.routine.helper;

import java.time.LocalTime;

/**
 * Created by Monjur-E-Morshed on 08-Sep-18.
 */
public class RoutineTime {
  LocalTime startTime;
  LocalTime endTime;

  public RoutineTime() {}

  public RoutineTime(LocalTime pStartTime, LocalTime pEndTime) {
    startTime = pStartTime;
    endTime = pEndTime;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime pStartTime) {
    startTime = pStartTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime pEndTime) {
    endTime = pEndTime;
  }

  @Override
  public String toString() {
    return "RoutineTime{" + "startTime=" + startTime + ", endTime=" + endTime + '}';
  }
}
