package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.Month;
import org.ums.domain.model.mutable.accounts.MutableMonth;
import org.ums.manager.ContentManager;

/**
 * Created by Monjur-E-Morshed on 11-Jan-18.
 */

public interface MonthManager extends ContentManager<Month, MutableMonth, Long> {
}
