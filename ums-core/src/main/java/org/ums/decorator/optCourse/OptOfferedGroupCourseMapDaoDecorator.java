package org.ums.decorator.optCourse;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupCourseMap;
import org.ums.manager.optCourse.OptOfferedGroupCourseMapManager;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class OptOfferedGroupCourseMapDaoDecorator
    extends
    ContentDaoDecorator<OptOfferedGroupCourseMap, MutableOptOfferedGroupCourseMap, Long, OptOfferedGroupCourseMapManager>
    implements OptOfferedGroupCourseMapManager {
}
