package org.ums.manager;

import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.readOnly.Course;

import java.util.List;

public interface CourseManager extends ContentManager<Course, MutableCourse, String> {
  public List<Course> getBySyllabus(final String pSyllabusId);
  public List<Course> getBySemesterProgram(final String pSemesterId,final String pProgramId);
  public List<Course> getOptionalCourses(final String pSyllabusId,final Integer pYear,final Integer pSemester);
}
