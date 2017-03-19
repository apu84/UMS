package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.immutable.Semester;

/**
 * Created by My Pc on 4/20/2016.
 */
public interface MutableSeatPlanGroup extends SeatPlanGroup, Editable<Integer>, MutableLastModifier,
    MutableIdentifier<Integer> {
  void setSemester(final Semester pSemester);

  void setProgram(final Program pProgram);

  void setProgramId(final int pProgramid);

  void setAcademicYear(final int pAcademicYear);

  void setAcademicSemester(final int pAcademicSemester);

  void setGroupNo(final int pGroupNo);

  void setExamType(final int mType);

  void setLastUpdateDate(final String pLastUpdateDate);

  void setTotalStudentNumber(final int pTotalStudentNumber);

  void setProgramShortName(final String pProgramShortName);
}
