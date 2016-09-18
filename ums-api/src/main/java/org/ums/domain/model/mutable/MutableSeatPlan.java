package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.*;

/**
 * Created by My Pc on 5/8/2016.
 */
public interface MutableSeatPlan extends SeatPlan,Mutable,MutableLastModifier,MutableIdentifier<Integer> {
  void setClassRoom(final ClassRoom pClassRoom);
  void setStudent(final Student pStudent);
  void setSemester(final Semester pSemester);
  void setClassRoomId(final int pClassRoomId);
  void setRowNo(final int pRowNo);
  void setColumnNo(final int pColumnNo);
  void setExamType(final int pExamType);
  void setGroupNo(final int pGroupNo);
  void setExamDate(final String pExamDate);
  void setApplicationType(final Integer pApplicationType);
}
