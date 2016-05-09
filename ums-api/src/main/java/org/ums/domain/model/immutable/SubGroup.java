package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableSubGroup;

import java.io.Serializable;

/**
 * Created by My Pc on 5/4/2016.
 */
public interface SubGroup extends Serializable,LastModifier,EditType<MutableSubGroup>,Identifier<Integer> {
  Semester getSemester() throws Exception;
  SeatPlanGroup getGroup() throws Exception;
  int subGroupNo();
  int getPosition();
  int getStudentNumber();
  int getExamType();
}
