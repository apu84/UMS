package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.SubGroup;

/**
 * Created by My Pc on 5/4/2016.
 */
public interface MutableSubGroup extends SubGroup,Mutable,MutableLastModifier,MutableIdentifier<Integer>{
  void setSemester(final Semester pSemester);
  void setGroup(final SeatPlanGroup pGroup);
  void setSubGroupNo(final int pSubGroupNo);
  void setPosition(final int pPosition);
  void setStudentNumber(final int pStudentNumber);
  void setExamType(final int pExamType);
  void setGroupId(final int pGroupId);
  void setProgramShortName(final String pProgramShortName);
  void setStudentYear(final Integer pStudentYear);
  void setStudentSemester(final Integer pStudentSemester);
}
