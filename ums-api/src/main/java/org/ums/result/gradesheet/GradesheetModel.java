package org.ums.result.gradesheet;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.immutable.UGRegistrationResult;

public interface GradesheetModel extends EditType<MutableGradesheetModel>, Identifier<Long> {
  String getStudentId();

  String getName();

  String getProgramName();

  String getDepartmentName();

  String getEnrollmentSemesterName();

  String getSemesterName();

  String getYear();

  String getAcademicSemester();

  String getSemesterCrHr();

  String getCumulativeCrHr();

  String getGpa();

  String getCGpa();

  CourseList<UGRegistrationResult> getRegularCourses();

  CourseList<UGRegistrationResult> getImprovementCourses();

  CourseList<UGRegistrationResult> getClearanceCourses();

  CourseList<CarryRegistrationResult> getCarryCourses();

  Remarks getRemarks();

  boolean isResultProcessed();
}
