package org.ums.tabulation;

import java.util.Map;

import org.ums.domain.model.immutable.UGRegistrationResult;

public interface TabulationCourseModel {
  Map<String, UGRegistrationResult> getRegularCoursesForCurrentSemester();

  Map<String, UGRegistrationResult> getClearanceCoursesForCurrentSemester();

  Map<String, UGRegistrationResult> getCarryCoursesForCurrentSemester();

  Map<String, UGRegistrationResult> getFailedCoursesForCurrentSemester();

  Map<String, UGRegistrationResult> getFailedCourseForPreviousSemester();
}
