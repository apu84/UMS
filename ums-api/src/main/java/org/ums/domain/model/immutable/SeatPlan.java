package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableSeatPlan;

import java.io.Serializable;

/**
 * Created by My Pc on 5/8/2016.
 */
public interface SeatPlan extends Serializable,LastModifier,EditType<MutableSeatPlan>,Identifier<Integer> {
  ClassRoom getClassRoom() throws Exception;
  SpStudent getStudent() throws Exception;
  Semester getSemester() throws Exception;
  int getClassRoomId();
  int getRowNo();
  int getColumnNo();
  int getExamType();
  int getGroupNo();
  String getExamDate();
}
