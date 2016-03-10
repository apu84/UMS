package org.ums.services.academic;

import org.ums.domain.model.readOnly.SemesterEnrollment;

public interface EnrollmentService {
  void saveEnrollment(final SemesterEnrollment.Type pType,
                      final Integer pNewSemesterId,
                      final Integer pProgramId,
                      final Integer pToYear,
                      final Integer pToAcademicSemester) throws Exception;

  void saveEnrollment(final SemesterEnrollment.Type pType,
                      final Integer pNewSemesterId,
                      final Integer pProgramId) throws Exception;
}
