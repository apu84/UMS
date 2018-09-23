package org.ums.decorator.optCourse;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupSubGroupMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupSubGroupMap;
import org.ums.manager.optCourse.OptOfferedGroupSubGroupMapManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class OptOfferedGroupSubGroupMapDaoDecorator
    extends
    ContentDaoDecorator<OptOfferedGroupSubGroupMap, MutableOptOfferedGroupSubGroupMap, Long, OptOfferedGroupSubGroupMapManager>
    implements OptOfferedGroupSubGroupMapManager {

  @Override
  public List<OptOfferedGroupSubGroupMap> getBySemesterId(Integer pSemesterId, Integer pProgramId, Integer pYear,
      Integer pSemester) {
    return getManager().getBySemesterId(pSemesterId, pProgramId, pYear, pSemester);
  }
}
