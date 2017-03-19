package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.UgAdmissionLog;

/**
 * Created by Monjur-E-Morshed on 01-Jan-17.
 */
public interface MutableUgAdmissionLog extends UgAdmissionLog, Editable<Integer>, MutableLastModifier,
    MutableIdentifier<Integer> {

  void setReceiptId(final String pReceiptId);

  void setSemesterId(final int pSemesterId);

  void setSemester(final Semester pSemester);

  void setLogText(final String pLogText);

  void setActorId(final String pActorId);

  void setActor(final Employee pEmployee);

  void setInsertionDate(final String pInsertionDate);
}
