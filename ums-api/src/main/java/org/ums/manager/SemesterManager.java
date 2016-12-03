package org.ums.manager;

import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.immutable.Semester;
import org.ums.enums.ProgramType;
import org.ums.enums.SemesterStatus;

import java.util.List;

/**
 * Created by Ifti on 27-Dec-15.
 */
public interface SemesterManager extends ContentManager<Semester, MutableSemester, Integer> {
  public List<Semester> getSemesters(final Integer pProgramType, final Integer pLimit);

  Semester getPreviousSemester(final Integer pSemesterId, final Integer pProgramTypeId);

  Semester getSemesterByStatus(final ProgramType programType, final SemesterStatus status);

  Semester getBySemesterName(final String pSemesterName, final Integer pProgramTypeId);

  Semester getActiveSemester(final Integer pProgramType);
}
