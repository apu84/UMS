package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.*;
import org.ums.enums.ApplicationStatus;

import java.util.List;

/**
 * Created by My Pc on 3/22/2016.
 */
public interface MutableSemesterWithdrawal extends SemesterWithdrawal,Mutable,MutableLastModifier,MutableIdentifier<Integer> {
  void setSemester(final Semester pSemester);
  void setStudent(final Student pStudent);
  void setCause(final String pCause);
  void setProgram(final Program pProgram);
  void setStatus(final int pStatus);
  void setAppDate(final String pAppDate);
}
