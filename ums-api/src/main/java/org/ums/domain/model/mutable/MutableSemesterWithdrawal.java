package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.SemesterWithdrawal;
import org.ums.domain.model.immutable.Student;

/**
 * Created by My Pc on 3/22/2016.
 */
public interface MutableSemesterWithdrawal extends SemesterWithdrawal, Mutable,
    MutableLastModifier, MutableIdentifier<Long> {
  void setSemester(final Semester pSemester);

  void setStudent(final Student pStudent);

  void setCause(final String pCause);

  void setProgram(final Program pProgram);

  void setStatus(final int pStatus);

  void setAppDate(final String pAppDate);

  void setComments(final String pComments);
}
