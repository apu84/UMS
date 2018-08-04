package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableEmpExamReserveDate;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public interface EmpExamReserveDate extends Serializable, LastModifier, EditType<MutableEmpExamReserveDate>,
    Identifier<Long> {
  Long getId();

  String getExamDate();

  Long getAttendantId();
}
