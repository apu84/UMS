package org.ums.academic.resource.optCourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.optCourse.optCourseHelper.OptCourseOfferResourceHelper;
import org.ums.manager.optCourse.OptCourseOfferManager;
import org.ums.resource.Resource;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class MutableOptCourseOfferResource extends Resource {
    @Autowired
    OptCourseOfferResourceHelper mHelper;
}
