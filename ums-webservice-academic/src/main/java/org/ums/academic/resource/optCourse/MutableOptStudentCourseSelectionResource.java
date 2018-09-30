package org.ums.academic.resource.optCourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.optCourse.optCourseHelper.OptStudentCourseSelectionHelper;
import org.ums.resource.Resource;

import java.awt.image.RescaleOp;

/**
 * Created by Monjur-E-Morshed on 9/29/2018.
 */
public class MutableOptStudentCourseSelectionResource extends Resource {
  @Autowired
  OptStudentCourseSelectionHelper mHelper;

}
