package org.ums.services.academic;

import org.ums.domain.model.immutable.SemesterEnrollment;
import org.ums.response.type.GenericResponse;

import java.util.Map;

public interface EnrollmentService {
  GenericResponse<Map> saveEnrollment(final SemesterEnrollment.Type pType,
      final Integer pNewSemesterId, final Integer pProgramId, final Integer pToYear,
      final Integer pToAcademicSemester);

  GenericResponse<Map> saveEnrollment(final SemesterEnrollment.Type pType,
      final Integer pNewSemesterId, final Integer pProgramId);
}
