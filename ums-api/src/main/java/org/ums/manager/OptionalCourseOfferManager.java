package org.ums.manager;

import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.dto.OptionalCourseApplicationStat;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.mutable.MutableOptionalCourseOffer;
import org.ums.domain.model.readOnly.ExamRoutine;
import org.ums.domain.model.readOnly.OptionalCourseOffer;

import java.util.List;


public interface OptionalCourseOfferManager extends ContentManager<OptionalCourseOffer, MutableOptionalCourseOffer, Object> {
  MutableOptionalCourseOffer getOptionalCourseOffer(int semesterId,int examType) throws Exception;
  List<OptionalCourseApplicationStat>  getOptionalCourseList() throws Exception;
  List<MutableCourse>  getApprovedCourseList() throws Exception;
  List<MutableCourse>  getCall4ApplicationCourseList() throws Exception;
  List<MutableCourse>  getAppliedStudentList() throws Exception;
}

