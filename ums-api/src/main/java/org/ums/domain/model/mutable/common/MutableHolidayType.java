package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.HolidayType;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Monjur-E-Morshed on 15-Jun-17.
 */
public interface MutableHolidayType extends HolidayType, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
  void setHolidayName(String pHolidayName);

  void setMoonDependency(HolidayType.SubjectToMoon pMoonDependency);
}
