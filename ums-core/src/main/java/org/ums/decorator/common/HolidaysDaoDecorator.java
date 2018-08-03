package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.Holidays;
import org.ums.domain.model.mutable.common.MutableHolidays;
import org.ums.manager.common.HolidaysManager;

import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 15-Jun-17.
 */
public class HolidaysDaoDecorator extends ContentDaoDecorator<Holidays, MutableHolidays, Long, HolidaysManager>
    implements HolidaysManager {

  @Override
  public List<Holidays> getHolidays(int pYear) {
    return getManager().getHolidays(pYear);
  }

  @Override
  public List<Holidays> getHolidays(Date pFromDate, Date pToDate) {
    return getManager().getHolidays(pFromDate, pToDate);
  }
}
