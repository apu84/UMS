package org.ums.decorator;

import org.ums.domain.model.immutable.ApplicationTesSelectedCourses;
import org.ums.domain.model.mutable.MutableApplicationTesSelectedCourses;
import org.ums.manager.ApplicationTesSelectedCourseManager;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public class ApplicationTesSelectedCoursesDaoDecorator
    extends
    ContentDaoDecorator<ApplicationTesSelectedCourses, MutableApplicationTesSelectedCourses, Long, ApplicationTesSelectedCourseManager>
    implements ApplicationTesSelectedCourseManager {
}
