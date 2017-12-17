package org.ums.services.academic;

import java.util.List;

import org.ums.academic.tabulation.model.TabulationCourseModelImpl;
import org.ums.domain.model.immutable.UGRegistrationResult;

public interface StudentCarryCourseService {
  TabulationCourseModelImpl findCoursesForTabulation(final List<UGRegistrationResult> pResultList, final int pSemesterId);
}
