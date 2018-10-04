package org.ums.manager.optCourse;

import org.ums.domain.model.immutable.optCourse.OptStudentCourseSelection;
import org.ums.domain.model.mutable.optCourse.MutableOptStudentCourseSelection;
import org.ums.manager.ContentManager;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public interface OptStudentCourseSelectionManager extends
    ContentManager<OptStudentCourseSelection, MutableOptStudentCourseSelection, Long> {
}
