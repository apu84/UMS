package org.ums.manager.optCourse;

import org.ums.domain.model.immutable.optCourse.OptOfferedGroupSubGroupMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupSubGroupMap;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public interface OptOfferedGroupSubGroupMapManager extends
    ContentManager<OptOfferedGroupSubGroupMap, MutableOptOfferedGroupSubGroupMap, Long> {
  List<OptOfferedGroupSubGroupMap> getBySemesterId(final Integer pSemesterId, final Integer pProgramId,
      final Integer pYear, final Integer pSemester);

}