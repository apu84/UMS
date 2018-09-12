package org.ums.domain.model.mutable.optCourse;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.optCourse.OptCourseOffer;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public interface MutableOptCourseOffer extends OptCourseOffer, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {

  void setId(Long pId);

  void setSemesterId(final Integer pSemesterId);

  void setDepartmentId(final String pDepartmentId);

  void setDepartment(final Department pDepartment);

  void setProgramId(final Integer pProgramId);

  void setProgramName(final String pProgramName);

  void setYear(final Integer pYear);

  void setSemester(final Integer pSemester);

  void setCourseId(final String pCourseId);

  void setCourses(final Course pCourses);

  void setCallForApplication(final Integer pCallForApplication);

  void setApproved(final Integer pApproved);

  void setTotal(final Integer pTotal);
}
