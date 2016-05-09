package org.ums.services.academic;

import org.ums.domain.model.immutable.SemesterEnrollment;
import org.ums.response.type.GenericResponse;

import java.util.Map;

/**
 * Created by My Pc on 4/21/2016.
 */
public interface SeatPlanService {
  GenericResponse<Map> generateGroup(final int pNewSemesterId, final int pExamType) throws Exception;
  GenericResponse<Map> generateSeatPlan(final int pSemesterId,final int pGroupNo,final int pExamType) throws  Exception;
}
