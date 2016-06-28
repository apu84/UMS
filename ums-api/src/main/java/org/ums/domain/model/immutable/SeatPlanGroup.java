package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableSeatPlanGroup;

import java.io.Serializable;

/**
 * Created by Munna on 4/20/2016.
 */
public interface SeatPlanGroup extends Serializable,LastModifier,EditType<MutableSeatPlanGroup>,Identifier<Integer> {
  Semester getSemester() throws Exception;
  Program getProgram() throws Exception;
  int getAcademicYear();
  int getAcademicSemester();
  int getGroupNo();
  String getLastUpdateDate();
  int getExamType();
  int getTotalStudentNumber();
  String getProgramName();
}
