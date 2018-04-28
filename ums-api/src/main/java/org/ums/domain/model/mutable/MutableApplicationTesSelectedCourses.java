package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.ApplicationTesSelectedCourses;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public interface MutableApplicationTesSelectedCourses extends ApplicationTesSelectedCourses, Editable<Long>,
    MutableLastModifier, MutableIdentifier<Long> {
  void setApplicationDate(final String pApplicationDate);

  void setCourseId(final String pReviewEligibleCourses);

  void setTeacherId(final String pTeacherId);

  void setSection(final String pSection);

  void setDeptId(final String pDeptId);

  void setSemester(final Integer pSemesterId);

  void setInsertionDate(final String pInsertionDate);

}
