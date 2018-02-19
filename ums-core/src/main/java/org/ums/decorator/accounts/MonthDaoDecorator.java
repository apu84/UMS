package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.Month;
import org.ums.domain.model.mutable.accounts.MutableMonth;
import org.ums.manager.accounts.MonthManager;

/**
 * Created by Monjur-E-Morshed on 11-Jan-18.
 */
public class MonthDaoDecorator extends ContentDaoDecorator<Month, MutableMonth, Long, MonthManager> implements
    MonthManager {
}
