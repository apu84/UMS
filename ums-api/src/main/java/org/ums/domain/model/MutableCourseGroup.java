package org.ums.domain.model;


public interface MutableCourseGroup extends CourseGroup, Mutable, MutableLastModifier, MutableIdentifier<Integer> {
  void setName(final String pName);

  void setSyllabus(final Syllabus pSyllabus);

  void setSyllabusId(final String pSyllabusId);
}
