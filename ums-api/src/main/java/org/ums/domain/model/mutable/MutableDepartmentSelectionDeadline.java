package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.DepartmentSelectionDeadline;
import org.ums.domain.model.immutable.Semester;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 27-Apr-17.
 */
public interface MutableDepartmentSelectionDeadline extends DepartmentSelectionDeadline, Editable<Integer>,
    MutableLastModifier, MutableIdentifier<Integer> {
  void setSemesterId(int pSemesterId);

  void setSemester(Semester pSemester);

  void setUnit(String pUnit);

  void setQuota(String pQuota);

  void setFromMeritSerialNumber(int pFromMeritSerialNumber);

  void setToMeritSerialNumber(int pToMeritSerialNumber);

  void setDeadline(Date pDeadline);
}
