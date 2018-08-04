package org.ums.manager;

import java.util.List;

import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.enums.ProgramType;
import org.ums.enums.SemesterStatus;

/**
 * Created by Ifti on 27-Dec-15.
 */
public interface SemesterManager extends ContentManager<Semester, MutableSemester, Integer> {

  List<Semester> getSemesters(final Integer pProgramType, final Integer pLimit);

  Semester getPreviousSemester(final Integer pSemesterId, final Integer pProgramTypeId);

  List<Semester> getPreviousSemesters(final Integer pSemesterId, final Integer pProgramTypeId);

  Semester getSemesterByStatus(final ProgramType programType, final SemesterStatus status);

  Semester getBySemesterName(final String pSemesterName, final Integer pProgramTypeId);

  Semester getActiveSemester(final Integer pProgramType);

  List<Semester> semestersAfter(final Integer pStartSemester, final Integer pEndSemester, final Integer pProgramTypeId);

  List<Semester> semestersAfter(final Integer pStartSemester, final Integer pProgramTypeId);

  Semester closestSemester(final Integer pCheckSemester, final List<Integer> pCheckAgainstSemesters);
}
