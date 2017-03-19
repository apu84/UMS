package org.ums.decorator;

import org.ums.domain.model.immutable.SemesterEnrollment;
import org.ums.domain.model.mutable.MutableSemesterEnrollment;
import org.ums.manager.SemesterEnrollmentManager;

import java.util.List;

public class SemesterEnrollmentDaoDecorator extends
    ContentDaoDecorator<SemesterEnrollment, MutableSemesterEnrollment, Long, SemesterEnrollmentManager> implements
    SemesterEnrollmentManager {
  @Override
  public List<SemesterEnrollment> getEnrollmentStatus(SemesterEnrollment.Type pType, Integer pProgramId,
      Integer pSemesterId) {
    return getManager().getEnrollmentStatus(pType, pProgramId, pSemesterId);
  }

  @Override
  public SemesterEnrollment getEnrollmentStatus(SemesterEnrollment.Type pType, Integer pProgramId, Integer pSemesterId,
      Integer pYear, Integer pAcademicSemester) {
    return getManager().getEnrollmentStatus(pType, pProgramId, pSemesterId, pYear, pAcademicSemester);
  }

  @Override
  public List<SemesterEnrollment> getEnrollmentStatus(Integer pProgramId, Integer pSemesterId) {
    return getManager().getEnrollmentStatus(pProgramId, pSemesterId);
  }
}
