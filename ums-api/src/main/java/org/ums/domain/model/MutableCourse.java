package org.ums.domain.model;


public interface MutableCourse extends Course, Mutable, MutableLastModifier, MutableIdentifier<String> {

  void setNo(final String pName);

  void setTitle(final String pTitle);

  void setCrHr(final float pCrHr);

  void setCourseType(final Course.CourseType pCourseType);

  void setCourseCategory(final Course.CourseCategory pCourseCategory);

  void setOfferedBy(final Department pDepartment);

  void setOfferedTo(final Department pDepartment);

  void setYear(final int pYear);

  void setSemester(final int pSemester);

  void setViewOrder(final int pViewOrder);

  void setCourseGroup(final CourseGroup pCourseGroup);

  void setSyllabus(final Syllabus pSyllabus);

  void setCourseGroupId(final int pCourseProgramId);

  void setSyllabusId(final String pSyllabusId);

  void setOfferedDepartmentId(final int pDepartmentId);
}
