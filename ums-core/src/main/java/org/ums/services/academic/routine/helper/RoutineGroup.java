package org.ums.services.academic.routine.helper;

import org.ums.domain.model.immutable.routine.Routine;
import org.ums.enums.routine.DayType;

import java.time.LocalTime;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 20-Sep-18.
 */
public class RoutineGroup {
  private int groupId;
  private DayType mDayType;
  private LocalTime startTime;
  private LocalTime endTime;
  private List<Routine> mRoutineList;

  public RoutineGroup() {}

  public RoutineGroup(int pGroupId, DayType pDayType, LocalTime pStartTime, LocalTime pEndTime,
      List<Routine> pRoutineList) {
    groupId = pGroupId;
    mDayType = pDayType;
    startTime = pStartTime;
    endTime = pEndTime;
    mRoutineList = pRoutineList;
  }

  public DayType getDayType() {
    return mDayType;
  }

  public void setDayType(DayType pDayType) {
    mDayType = pDayType;
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int pGroupId) {
    groupId = pGroupId;
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

  public List<Routine> getRoutineList() {
    return mRoutineList;
  }

  public void setRoutineList(List<Routine> pRoutineList) {
    mRoutineList = pRoutineList;
  }
}
