package org.ums.academic.resource.teacher.evaluation.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.manager.ApplicationTesSelectedCourseManager;
import org.ums.resource.Resource;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public class MutableApplicationTesSelectedCoursesResource extends Resource {
  @Autowired
  ApplicationTesSelectedCourseManager mApplicationTesSelectedCourseManager;
}
