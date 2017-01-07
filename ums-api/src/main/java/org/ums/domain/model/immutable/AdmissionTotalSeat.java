package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;
import org.ums.enums.*;
import org.ums.enums.ProgramType;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 02-Jan-17.
 */
public interface AdmissionTotalSeat extends Serializable, EditType<MutableAdmissionTotalSeat>,
    LastModifier, Identifier<Integer> {
  int getSemesterId();

  Semester getSemester();

  int getProgramId();

  Program getProgram();

  int getTotalSeat();

  ProgramType getProgramType();
}
