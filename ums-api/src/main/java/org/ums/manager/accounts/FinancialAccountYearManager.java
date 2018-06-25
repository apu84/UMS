package org.ums.manager.accounts;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableFinancialAccountYear;
import org.ums.manager.ContentManager;

import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public interface FinancialAccountYearManager extends
    ContentManager<FinancialAccountYear, MutableFinancialAccountYear, Long> {

  List<FinancialAccountYear> getAll(Company pCompany);

  MutableFinancialAccountYear getOpenedFinancialAccountYear(Company pCompany);

  boolean exists(Date pStartDate, Date pEndDate);

  void deleteAll();
}
