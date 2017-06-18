package org.ums.resource.holidays;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

/**
 * Created by Monjur-E-Morshed on 17-Jun-17.
 */
public class MutableHolidayTypeResource extends Resource {

  @Autowired
  protected HolidayTypeResourceHelper mHelper;
}
