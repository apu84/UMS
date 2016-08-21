package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.immutable.SeatPlanReport;

/**
 * Created by My Pc on 20-Aug-16.
 */
public interface MutableSeatPlanReport extends SeatPlanReport,Mutable{
  void setRoomNo(String pRoomNo);
  void setProgramName(String pProgramShortName);
  void setCourseTitle(String pCourseTitle);
  void setCourseNo(String pCourseNo);
  void setCurrentYear(Integer pCurrentYear);
  void setCurrentSemester(Integer pCurrentSemester);
  void setStudentId(String pStudentId);
}
