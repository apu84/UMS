package org.ums.decorator;

import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.immutable.Semester;
import org.ums.enums.ProgramType;
import org.ums.enums.SemesterStatus;
import org.ums.manager.SemesterManager;

import java.util.List;

/**
 * Created by Ifti on 27-Dec-15.
 */
public class SemesterDaoDecorator extends
    ContentDaoDecorator<Semester, MutableSemester, Integer, SemesterManager> implements
    SemesterManager {
  @Override
  public List<Semester> getSemesters(Integer pProgramType, Integer pLimit) throws Exception {
    return getManager().getSemesters(pProgramType, pLimit);
  }

  @Override
  public Semester getPreviousSemester(Integer pSemesterId, Integer pProgramTypeId) throws Exception {
    return getManager().getPreviousSemester(pSemesterId, pProgramTypeId);
  }

  @Override
  public Semester getSemesterByStatus(ProgramType programType, SemesterStatus status)
      throws Exception {
    return getManager().getSemesterByStatus(programType, status);
  }

  @Override
  public Semester getBySemesterName(String pSemesterName, Integer pProgramTypeId) throws Exception {
    return getManager().getBySemesterName(pSemesterName, pProgramTypeId);
  }

  @Override
  public Semester getActiveSemester(Integer pProgramType) {
    return getManager().getActiveSemester(pProgramType);
  }
}
