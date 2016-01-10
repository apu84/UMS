package org.ums.manager;

import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.mutable.MutableSemesterSyllabusMap;
import org.ums.domain.model.regular.Semester;
import org.ums.domain.model.regular.SemesterSyllabusMap;

import java.util.List;

/**
 * Created by Ifti on 08-Jan-16.
 */
public interface SemesterSyllabusMapManager extends ContentManager<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer> {
  public List<SemesterSyllabusMap> getMapsByProgramSemester(final Integer pProgramId,final Integer pSemesterId) throws Exception;
  public  SemesterSyllabusMap get(final Integer pMapId) throws Exception;
}
