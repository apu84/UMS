package org.ums.manager;

import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.readOnly.Semester;

import java.util.List;

/**
 * Created by Ifti on 27-Dec-15.
 */
public interface SemesterManager extends ContentManager<Semester, MutableSemester, Integer> {
  public List<Semester> getSemesters(final Integer pProgramType, final Integer pLimit) throws Exception;

  Semester getPreviousSemester(final Integer pSemesterId, final Integer pProgramTypeId) throws Exception;
}
