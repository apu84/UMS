package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.dto.OptionalCourseApplicationStat;
import org.ums.domain.model.readOnly.ExamRoutine;
import org.ums.domain.model.readOnly.OptionalCourseOffer;

import java.util.List;

public interface MutableOptionalCourseOffer extends OptionalCourseOffer, Mutable {
  void setOptionalCourseList(final List<OptionalCourseApplicationStat> pOptionalCourseList);
  void setApprovedCourseList(final List<MutableCourse> pCourseList);
  void setCall4ApplicationCourseList(final List<MutableCourse> pCourseList);
  void setAppliedStudentList(final List<MutableCourse> pCourseList);
}
