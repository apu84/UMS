package org.ums.manager.accounts;

import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableFinancialAccountYear;
import org.ums.manager.ContentManager;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public interface FinancialAccountYearManager extends
    ContentManager<FinancialAccountYear, MutableFinancialAccountYear, Long> {

}
