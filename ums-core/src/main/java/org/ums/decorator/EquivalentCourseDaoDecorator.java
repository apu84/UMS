package org.ums.decorator;

import org.ums.domain.model.immutable.EquivalentCourse;
import org.ums.domain.model.mutable.MutableEquivalentCourse;
import org.ums.manager.EquivalentCourseManager;

public class EquivalentCourseDaoDecorator extends
    ContentDaoDecorator<EquivalentCourse, MutableEquivalentCourse, Long, EquivalentCourseManager>
    implements EquivalentCourseManager {
}
