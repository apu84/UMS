package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.mutable.MutableSemesterSyllabusMap;
import org.ums.domain.model.regular.Semester;
import org.ums.domain.model.regular.SemesterSyllabusMap;
import org.ums.manager.SemesterManager;
import org.ums.manager.SemesterSyllabusMapManager;

import java.util.List;

/**
 * Created by Ifti on 08-Jan-16.
 */
public class SemesterSyllabusMapDaoDecorator extends ContentDaoDecorator<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer> implements SemesterSyllabusMapManager {
  private SemesterSyllabusMapManager mManager;

  @Override
  public SemesterSyllabusMapManager getManager() {
    return mManager;
  }


  @Override
  public List<SemesterSyllabusMap> getMapsByProgramSemester(final Integer pProgramId,final Integer pSemesterId) throws Exception {
    return getManager().getMapsByProgramSemester(pProgramId, pSemesterId);
  }
  @Override
  public SemesterSyllabusMap getMapsByYearSemester(final Integer pProgramId,final Integer pSemesterId,final Integer pYear,final Integer pSemester) throws Exception {
    return getManager().getMapsByYearSemester(pProgramId, pSemesterId, pYear,pSemester);
  }


}
