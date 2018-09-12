package org.ums.decorator.optCourse;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptCourseOffer;
import org.ums.domain.model.mutable.optCourse.MutableOptCourseOffer;
import org.ums.manager.optCourse.OptCourseOfferManager;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class OptCourseOfferDaoDecorator extends
    ContentDaoDecorator<OptCourseOffer, MutableOptCourseOffer, Long, OptCourseOfferManager> implements
    OptCourseOfferManager {

}
