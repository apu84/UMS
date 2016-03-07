package org.ums.manager;

import org.ums.domain.model.mutable.MutableSemesterEnrollment;
import org.ums.domain.model.readOnly.SemesterEnrollment;

import java.util.List;

public interface SemesterEnrollmentManager extends ContentManager<SemesterEnrollment, MutableSemesterEnrollment, Integer> {
  List<SemesterEnrollment> getEnrollmentStatus(final SemesterEnrollment.Type pType, final Integer pProgramId,
                                               final Integer pSemesterId);

  List<SemesterEnrollment> getEnrollmentStatus(final SemesterEnrollment.Type pType, final Integer pProgramId,
                                               final Integer pSemesterId,
                                               final Integer pYear,
                                               final Integer pAcademicSemester);
}
