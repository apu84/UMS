package org.ums.services.academic;

import org.ums.domain.model.readOnly.SemesterEnrollment;
import org.ums.response.type.GenericResponse;

import java.util.Map;

public interface EnrollmentService {
  GenericResponse<Map> saveEnrollment(final SemesterEnrollment.Type pType,
                      final Integer pNewSemesterId,
                      final Integer pProgramId,
                      final Integer pToYear,
                      final Integer pToAcademicSemester) throws Exception;

  GenericResponse<Map> saveEnrollment(final SemesterEnrollment.Type pType,
                      final Integer pNewSemesterId,
                      final Integer pProgramId) throws Exception;
}
