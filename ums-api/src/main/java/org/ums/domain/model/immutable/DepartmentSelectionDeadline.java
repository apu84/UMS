package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableDepartmentSelectionDeadline;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 27-Apr-17.
 */
public interface DepartmentSelectionDeadline extends Serializable, EditType<MutableDepartmentSelectionDeadline>,
    LastModifier, Identifier<Integer> {
  int getSemesterId();

  Semester getSemester();

  String getUnit();

  String getQuota();

  int getFromMeritSerialNumber();

  int getToMeritSerialNumber();

  Date getDeadline();
}
