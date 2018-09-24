package org.ums.manager.optCourse;

import org.ums.domain.model.immutable.optCourse.OptOfferedSubGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedSubGroupCourseMap;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public interface OptOfferedSubGroupCourseMapManager extends
    ContentManager<OptOfferedSubGroupCourseMap, MutableOptOfferedSubGroupCourseMap, Long> {
  List<OptOfferedSubGroupCourseMap> getSubGroupCourses(Integer pSemesterId, Integer pProgramId, Integer pYear,
      Integer pSemester);

}
