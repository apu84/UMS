package org.ums.domain.model;


import java.io.Serializable;

public interface CourseGroup extends Serializable, EditType<MutableCourseGroup>, LastModifier, Identifier<Integer> {
  String getName();

  Syllabus getSyllabus() throws Exception;

  String getSyllabusId();
}
