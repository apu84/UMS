package org.ums.manager;

import org.ums.domain.model.mutable.MutableSemesterEnrollment;
import org.ums.domain.model.immutable.SemesterEnrollment;

import java.util.List;

public interface SemesterEnrollmentManager extends ContentManager<SemesterEnrollment, MutableSemesterEnrollment, Long> {
  List<SemesterEnrollment> getEnrollmentStatus(final SemesterEnrollment.Type pType, final Integer pProgramId,
      final Integer pSemesterId);

  SemesterEnrollment getEnrollmentStatus(final SemesterEnrollment.Type pType, final Integer pProgramId,
      final Integer pSemesterId, final Integer pYear, final Integer pAcademicSemester);

  List<SemesterEnrollment> getEnrollmentStatus(final Integer pProgramId, final Integer pSemesterId);
}
