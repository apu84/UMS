package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableSemesterEnrollment;
import org.ums.domain.model.readOnly.SemesterEnrollment;
import org.ums.manager.SemesterEnrollmentManager;

public class SemesterEnrollmentDaoDecorator
    extends ContentDaoDecorator<SemesterEnrollment, MutableSemesterEnrollment, Integer, SemesterEnrollmentManager>
    implements SemesterEnrollmentManager {

}
