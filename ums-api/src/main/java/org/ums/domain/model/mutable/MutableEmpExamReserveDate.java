package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.EmpExamReserveDate;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public interface MutableEmpExamReserveDate extends EmpExamReserveDate, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
  void setId(final Long pId);

  void setExamDate(final String pExamDate);

  void setAttendantId(final Long pAttendantId);
}
