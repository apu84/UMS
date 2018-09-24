package org.ums.manager.optCourse;

import org.ums.domain.model.immutable.optCourse.OptOfferedGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupCourseMap;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public interface OptOfferedGroupCourseMapManager extends
    ContentManager<OptOfferedGroupCourseMap, MutableOptOfferedGroupCourseMap, Long> {
  List<OptOfferedGroupCourseMap> getInfo(final Integer pSemesterId, final Integer pProgramId, final Integer pYear,
      final Integer pSemester);
}
