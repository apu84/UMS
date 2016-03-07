package org.ums.domain.model.readOnly;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.dto.OptionalCourseApplicationStat;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.mutable.MutableOptionalCourseOffer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ifti on 07-Mar-16.
 */
public interface OptionalCourseOffer extends Serializable, EditType<MutableOptionalCourseOffer> {
  List<OptionalCourseApplicationStat>  getOptionalCourseList();
  List<MutableCourse>  getApprovedCourseList();
  List<MutableCourse>  getCall4ApplicationCourseList();
  List<MutableCourse>  getAppliedStudentList();
}
