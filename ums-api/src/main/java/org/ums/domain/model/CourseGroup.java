package org.ums.domain.model;


import java.io.Serializable;

//Course Group for Optional Courses
public interface CourseGroup extends Serializable, EditType<MutableCourseGroup>, LastModifier, Identifier<Integer> {
  String getName();

  Syllabus getSyllabus() throws Exception;

  String getSyllabusId();
}
