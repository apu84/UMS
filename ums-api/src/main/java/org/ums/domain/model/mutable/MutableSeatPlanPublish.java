package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.SeatPlanPublish;
import org.ums.enums.ExamType;

/**
 * Created by My Pc on 8/2/2016.
 */
public interface MutableSeatPlanPublish extends SeatPlanPublish, Mutable, MutableLastModifier,
    MutableIdentifier<Integer> {
  void setSemester(Semester pSemester);

  void setSemesterId(Integer pSemesterId);

  void setExamType(ExamType pExamType);

  void setExamDate(String pExamDate);

  void setPublishStatus(Integer isPublished);
}
