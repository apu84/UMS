package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.SeatPlanGroup;

/**
 * Created by My Pc on 4/20/2016.
 */
public interface MutableSeatPlanGroup extends SeatPlanGroup,Mutable,MutableLastModifier,MutableIdentifier<Integer>{
  void setSemester(final Semester pSemester);
  void setProgram(final Program pProgram);
  void setAcademicYear(final int pAcademicYear);
  void setAcademicSemester(final int pAcademicSemester);
  void setGroupNo(final int pGroupNo);
  void setExamType(final int mType);
  void setLastUpdateDate(final String pLastUpdateDate);
}
