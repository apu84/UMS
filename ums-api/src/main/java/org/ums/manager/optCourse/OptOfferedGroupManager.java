package org.ums.manager.optCourse;

import org.ums.domain.model.immutable.optCourse.OptOfferedGroup;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroup;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public interface OptOfferedGroupManager extends ContentManager<OptOfferedGroup, MutableOptOfferedGroup, Long> {
  OptOfferedGroup getByGroupName(final String pGroupName);

  List<OptOfferedGroup> getBySemesterId(final Integer pSemesterId, final Integer pProgramId, final Integer pYear,
      final Integer pSemester);

}
