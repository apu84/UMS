package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.ExamRoutineResourceHelper;
import org.ums.common.academic.resource.helper.OptionalCourseOfferResourceHelper;

/**
 * Created by Ifti on 08-Mar-16.
 */
public class MutableOptionalCourseOfferResource  extends Resource {

  @Autowired
  OptionalCourseOfferResourceHelper mResourceHelper;


}
