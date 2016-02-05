package org.ums.manager;

import org.ums.domain.model.dto.SemesterSyllabusMapDto;
import org.ums.domain.model.mutable.MutableSemesterSyllabusMap;
import org.ums.domain.model.readOnly.SemesterSyllabusMap;
import org.ums.domain.model.readOnly.Syllabus;

import java.util.List;

/**
 * Created by Ifti on 08-Jan-16.
 */
public interface SemesterSyllabusMapManager extends ContentManager<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer> {
  public List<SemesterSyllabusMap> getMapsByProgramSemester(final Integer pProgramId, final Integer pSemesterId) throws Exception;

  public SemesterSyllabusMap get(final Integer pMapId) throws Exception;

  public void copySyllabus(final SemesterSyllabusMapDto pSemesterSyllabusMapDto) throws Exception;

  List<Syllabus> getSyllabusForSemester(final Integer pProgramId, final Integer pSemesterId);

  Syllabus getSyllabusForSemester(final Integer pProgramId, final Integer pSemesterId, final Integer pYear, final Integer pSemester);
}
