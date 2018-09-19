package org.ums.decorator.optCourse;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptCourseGroup;
import org.ums.domain.model.mutable.optCourse.MutableOptCourseGroup;
import org.ums.manager.optCourse.OptCourseGroupManager;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class OptCourseGroupDaoDecorator extends
    ContentDaoDecorator<OptCourseGroup, MutableOptCourseGroup, Long, OptCourseGroupManager> implements
    OptCourseGroupManager {
}
