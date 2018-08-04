package org.ums.manager.common;

import org.ums.domain.model.immutable.common.Holidays;
import org.ums.domain.model.mutable.common.MutableHolidays;
import org.ums.manager.ContentManager;

import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 15-Jun-17.
 */
public interface HolidaysManager extends ContentManager<Holidays, MutableHolidays, Long> {
  List<Holidays> getHolidays(final int pYear);

  List<Holidays> getHolidays(final Date pFromDate, final Date pToDate);
}
