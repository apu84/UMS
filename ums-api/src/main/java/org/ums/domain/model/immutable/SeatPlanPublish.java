package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableSeatPlanPublish;
import org.ums.enums.ExamType;

import java.io.Serializable;

/**
 * Created by My Pc on 8/2/2016.
 */
public interface SeatPlanPublish extends Serializable,LastModifier,EditType<MutableSeatPlanPublish>,Identifier<Integer> {
  Semester getSemester() throws Exception;
  Integer getSemesterId();
  ExamType getExamType();
  String getExamDate();
  Integer getPublishStatus();
}
