package org.ums.services.academic;

import org.ums.domain.model.immutable.SemesterEnrollment;
import org.ums.response.type.GenericResponse;

import java.util.Map;

/**
 * Created by My Pc on 4/21/2016.
 */
public interface SeatPlanService {
  GenericResponse<Map> saveSeatPlan(final int pNewSemesterId,final int pExamType) throws Exception;
}
