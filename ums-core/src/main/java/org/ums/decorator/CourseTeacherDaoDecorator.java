package org.ums.decorator;

import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.manager.AssignedTeacherManager;

public class CourseTeacherDaoDecorator
    extends AssignedTeacherDaoDecorator<CourseTeacher, MutableCourseTeacher, Integer, AssignedTeacherManager<CourseTeacher, MutableCourseTeacher, Integer>> {

}
