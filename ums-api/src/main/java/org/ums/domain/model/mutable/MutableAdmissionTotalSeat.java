package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AdmissionTotalSeat;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;

/**
 * Created by Monjur-E-Morshed on 02-Jan-17.
 */
public interface MutableAdmissionTotalSeat extends AdmissionTotalSeat, Mutable, MutableLastModifier,
    MutableIdentifier<Integer> {
  void setSemesterId(final int pSemesterId);

  void setSemester(final Semester pSemester);

  void setProgramId(final int pProgramid);

  void setProgram(final Program pProgram);

  void setTotalSeat(final int pTotalSeat);

  void setProgramType(final ProgramType pProgramType);

  void setQuotaType(final QuotaType pQuotaType);
}
