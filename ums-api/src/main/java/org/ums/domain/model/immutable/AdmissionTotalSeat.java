package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 02-Jan-17.
 */
public interface AdmissionTotalSeat extends Serializable, EditType<MutableAdmissionTotalSeat>,
    LastModifier, Identifier<Integer> {
  int getSemesterId(final int pSemesterId);

  Semester getSemester(final Semester pSemester);

  int getProgramId(final int pProgramId);

  Program getProgram(final Program pProgram);

  int getTotalSeat(final int pTotalSeat);

}
