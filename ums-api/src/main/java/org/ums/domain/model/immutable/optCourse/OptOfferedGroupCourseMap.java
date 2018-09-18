package org.ums.domain.model.immutable.optCourse;

import org.ums.domain.model.immutable.Course;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public interface OptOfferedGroupCourseMap {
    Long getId();

    Long getGroupId();

    String getCourseId();

    Course getCourses();
}
