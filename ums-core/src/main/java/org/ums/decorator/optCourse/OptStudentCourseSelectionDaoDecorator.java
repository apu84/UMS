package org.ums.decorator.optCourse;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptStudentCourseSelection;
import org.ums.domain.model.mutable.optCourse.MutableOptStudentCourseSelection;
import org.ums.manager.optCourse.OptStudentCourseSelectionManager;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public class OptStudentCourseSelectionDaoDecorator
    extends
    ContentDaoDecorator<OptStudentCourseSelection, MutableOptStudentCourseSelection, Long, OptStudentCourseSelectionManager>
    implements OptStudentCourseSelectionManager {
}
