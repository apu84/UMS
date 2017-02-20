package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.EnrollmentFromTo;
import org.ums.domain.model.immutable.Program;

public interface MutableEnrollmentFromTo extends EnrollmentFromTo, MutableLastModifier,
    MutableIdentifier<Long>, Mutable {
  void setProgramId(final Integer pProgramId);

  void setProgram(final Program pProgram);

  void setFromYear(final Integer pFromYear);

  void setFromSemester(final Integer pFromSemester);

  void setToYear(final Integer pToYear);

  void setToSemester(final Integer pToSemester);
}
