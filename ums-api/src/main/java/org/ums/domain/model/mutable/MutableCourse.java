package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.*;
import org.ums.enums.CourseCategory;
import org.ums.enums.CourseType;

public interface MutableCourse extends Course, Editable<String>, MutableLastModifier, MutableIdentifier<String> {

  void setNo(final String pNo);

  void setTitle(final String pTitle);

  void setCrHr(final float pCrHr);

  void setOfferedDepartmentId(final String pDepartmentId);

  void setOfferedBy(final Department pDepartment);

  void setOfferedToDepartment(final Department pDepartment);

  void setOfferedToProgram(final Program pProgram);

  void setOfferedToProgramId(final Integer pProgramId);

  void setYear(final Integer pYear);

  void setSemester(final Integer pSemester);

  void setViewOrder(final Integer pViewOrder);

  void setCourseGroupId(final Integer pGroupId);

  void setCourseGroup(final CourseGroup pOptionalGroup);

  void setCourseType(final CourseType pCourseType);

  void setCourseCategory(final CourseCategory pCourseCategory);

  void setPairCourseId(final String pPairCourseId);

  void setTotalApplied(final Integer pTotalApplied);

}
