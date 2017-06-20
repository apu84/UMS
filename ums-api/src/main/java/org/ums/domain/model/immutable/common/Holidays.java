package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableHolidays;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 15-Jun-17.
 */
public interface Holidays extends Serializable, LastModifier, EditType<MutableHolidays>, Identifier<Long> {
  HolidayType getHolidayType();

  int getYear();

  Date getFromDate();

  Date getToDate();
}
