package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.HolidayType;
import org.ums.domain.model.mutable.common.MutableHolidayType;
import org.ums.manager.common.HolidayTypeManager;

/**
 * Created by Monjur-E-Morshed on 15-Jun-17.
 */
public class HolidayTypeDaoDecorator extends
    ContentDaoDecorator<HolidayType, MutableHolidayType, Long, HolidayTypeManager> implements HolidayTypeManager {

}
