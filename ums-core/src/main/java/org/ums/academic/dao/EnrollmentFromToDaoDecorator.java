package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableEnrollmentFromTo;
import org.ums.domain.model.readOnly.EnrollmentFromTo;
import org.ums.manager.EnrollmentFromToManager;

import java.util.List;

public class EnrollmentFromToDaoDecorator
    extends ContentDaoDecorator<EnrollmentFromTo, MutableEnrollmentFromTo, Integer, EnrollmentFromToManager>
    implements EnrollmentFromToManager {
    @Override
    public List<EnrollmentFromTo> getEnrollmentFromTo(final Integer pProgramId) {
        return getManager().getEnrollmentFromTo(pProgramId);
    }
}
