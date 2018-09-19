package org.ums.academic.resource.optCourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.optCourse.optCourseHelper.OptOfferedSubGroupCourseMapResourceHelper;
import org.ums.resource.Resource;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class MutableOptOfferedSubGroupCourseMapResource extends Resource {
  @Autowired
  OptOfferedSubGroupCourseMapResourceHelper mHelper;
}
