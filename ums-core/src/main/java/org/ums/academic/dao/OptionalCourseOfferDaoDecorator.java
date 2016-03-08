package org.ums.academic.dao;

import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.dto.OptionalCourseApplicationStat;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.mutable.MutableOptionalCourseOffer;
import org.ums.domain.model.readOnly.ExamRoutine;
import org.ums.domain.model.readOnly.OptionalCourseOffer;
import org.ums.manager.ExamRoutineManager;
import org.ums.manager.OptionalCourseOfferManager;

import java.util.List;

/**
 * Created by Ifti on 08-Mar-16.
 */
public class OptionalCourseOfferDaoDecorator extends ContentDaoDecorator<OptionalCourseOffer, MutableOptionalCourseOffer, Object, OptionalCourseOfferManager> implements OptionalCourseOfferManager {
  @Override
  public MutableOptionalCourseOffer getOptionalCourseOffer(int semesterId,int examTypeId) throws Exception {
    //return getManager().getExamRoutine(semesterId,examTypeId);
    return null;
  }

  @Override
  public List<OptionalCourseApplicationStat> getOptionalCourseList() throws Exception {
    return null;
  }

  @Override
  public List<MutableCourse> getApprovedCourseList() throws Exception {
    return null;
  }

  @Override
  public List<MutableCourse> getCall4ApplicationCourseList() throws Exception {
    return null;
  }

  @Override
  public List<MutableCourse> getAppliedStudentList() throws Exception {
    return null;
  }
}