package org.ums.domain.model.mutable;

import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.enums.ExamType;

public interface MutableUGRegistrationResult extends UGRegistrationResult,
    MutableUGBaseRegistration {

}
