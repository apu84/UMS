package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.enums.ExamType;

public interface UGRegistrationResult extends UGBaseRegistration,
    EditType<MutableUGRegistrationResult> {
  /*
   * Student getStudent(); Semester getSemester(); Course getCourse(); String getGradeLetter();
   * ExamType getExamType(); CourseRegType getRegType();
   */
}
