package org.ums.domain.model.mutable;

import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.CourseGroup;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.enums.CourseCategory;
import org.ums.enums.CourseType;

public interface MutableCourse extends Course, Mutable, MutableLastModifier, MutableIdentifier<String> {

  void setNo(final String pName);

  void setTitle(final String pTitle);

  void setCrHr(final float pCrHr);

  void setCourseType(final CourseType pCourseType);

  void setCourseCategory(final CourseCategory pCourseCategory);

  void setOfferedBy(final Department pDepartment);

  void setOfferedTo(final Department pDepartment);

  void setYear(final int pYear);

  void setSemester(final int pSemester);

  void setViewOrder(final int pViewOrder);

  void setCourseGroup(final CourseGroup pCourseGroup);

  void setSyllabus(final Syllabus pSyllabus);

  void setCourseGroupId(final int pCourseProgramId);

  void setSyllabusId(final String pSyllabusId);

  void setOfferedDepartmentId(final String pDepartmentId);

  void setPairCourseId(final String pPairCourseId);

  void setTotalApplied(final int pTotalApplied);
}
