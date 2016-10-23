package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.mutable.MutableSeatPlanReport;

import java.io.Serializable;

/**
 * Created by My Pc on 20-Aug-16.
 */
public interface SeatPlanReport extends Serializable, EditType<MutableSeatPlanReport> {
  String getRoomNo();

  String getProgramName();

  String getCourseTitle();

  String getCourseNo();

  String getExamDate();

  Integer getCurrentYear();

  Integer getCurrentSemester();

  String getStudentId();
}
