package org.ums.academic.tabulation.model;

import java.util.Map;

import org.ums.domain.model.immutable.UGRegistrationResult;

public class TabulationCourseModelImpl implements org.ums.tabulation.TabulationCourseModel {
  Map<String, UGRegistrationResult> mRegularCoursesForCurrentSemester;
  Map<String, UGRegistrationResult> mClearanceCoursesForCurrentSemester;
  Map<String, UGRegistrationResult> mFailedCoursesForCurrentSemester;
  Map<String, UGRegistrationResult> mFailedCoursesForPreviousSemester;
  Map<String, UGRegistrationResult> mCarryCoursesForCurrentSemester;

  public TabulationCourseModelImpl(Map<String, UGRegistrationResult> pRegularCoursesForCurrentSemester,
      Map<String, UGRegistrationResult> pClearanceCoursesForCurrentSemester,
      Map<String, UGRegistrationResult> pCarryCoursesForCurrentSemester,
      Map<String, UGRegistrationResult> pFailedCoursesForCurrentSemester,
      Map<String, UGRegistrationResult> pFailedCoursesForPreviousSemester) {
    mRegularCoursesForCurrentSemester = pRegularCoursesForCurrentSemester;
    mClearanceCoursesForCurrentSemester = pClearanceCoursesForCurrentSemester;
    mFailedCoursesForCurrentSemester = pFailedCoursesForCurrentSemester;
    mFailedCoursesForPreviousSemester = pFailedCoursesForPreviousSemester;
    mCarryCoursesForCurrentSemester = pCarryCoursesForCurrentSemester;
  }

  public Map<String, UGRegistrationResult> getRegularCoursesForCurrentSemester() {
    return mRegularCoursesForCurrentSemester;
  }

  public Map<String, UGRegistrationResult> getFailedCoursesForCurrentSemester() {
    return mFailedCoursesForCurrentSemester;
  }

  @Override
  public Map<String, UGRegistrationResult> getFailedCourseForPreviousSemester() {
    return mFailedCoursesForPreviousSemester;
  }

  @Override
  public Map<String, UGRegistrationResult> getClearanceCoursesForCurrentSemester() {
    return mClearanceCoursesForCurrentSemester;
  }

  @Override
  public Map<String, UGRegistrationResult> getCarryCoursesForCurrentSemester() {
    return mCarryCoursesForCurrentSemester;
  }
}
