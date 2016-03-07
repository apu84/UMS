package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableSemesterEnrollment;
import org.ums.domain.model.readOnly.SemesterEnrollment;
import org.ums.manager.SemesterEnrollmentManager;

import java.util.List;

public class SemesterEnrollmentDaoDecorator
    extends ContentDaoDecorator<SemesterEnrollment, MutableSemesterEnrollment, Integer, SemesterEnrollmentManager>
    implements SemesterEnrollmentManager {
    @Override
    public List<SemesterEnrollment> getEnrollmentStatus(SemesterEnrollment.Type pType, Integer pProgramId, Integer pSemesterId) {
        return getManager().getEnrollmentStatus(pType, pProgramId, pSemesterId);
    }

    @Override
    public List<SemesterEnrollment> getEnrollmentStatus(SemesterEnrollment.Type pType, Integer pProgramId, Integer pSemesterId,
                                                        Integer pYear, Integer pAcademicSemester) {
        return getManager().getEnrollmentStatus(pType, pProgramId, pSemesterId, pYear, pAcademicSemester);
    }
}
