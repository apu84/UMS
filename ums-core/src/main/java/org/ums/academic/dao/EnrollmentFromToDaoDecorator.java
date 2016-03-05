package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableEnrollmentFromTo;
import org.ums.domain.model.readOnly.EnrollmentFromTo;
import org.ums.manager.EnrollmentFromToManager;

public class EnrollmentFromToDaoDecorator
    extends ContentDaoDecorator<EnrollmentFromTo, MutableEnrollmentFromTo, Integer, EnrollmentFromToManager>
    implements EnrollmentFromToManager {
}
