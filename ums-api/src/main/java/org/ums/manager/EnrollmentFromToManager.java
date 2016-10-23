package org.ums.manager;

import org.ums.domain.model.mutable.MutableEnrollmentFromTo;
import org.ums.domain.model.immutable.EnrollmentFromTo;

import java.util.List;

public interface EnrollmentFromToManager extends
    ContentManager<EnrollmentFromTo, MutableEnrollmentFromTo, Integer> {
  List<EnrollmentFromTo> getEnrollmentFromTo(final Integer pProgramId);
}
