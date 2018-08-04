package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableFinancialAccountYear;
import org.ums.manager.accounts.FinancialAccountYearManager;

import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class FinancialAccountYearDaoDecorator extends
    ContentDaoDecorator<FinancialAccountYear, MutableFinancialAccountYear, Long, FinancialAccountYearManager> implements
    FinancialAccountYearManager {

  @Override
  public MutableFinancialAccountYear getOpenedFinancialAccountYear(Company pCompany) {
    return getManager().getOpenedFinancialAccountYear(pCompany);
  }

  @Override
  public List<FinancialAccountYear> getAll(Company pCompany) {
    return getManager().getAll(pCompany);
  }

  @Override
  public void deleteAll() {
    getManager().deleteAll();
  }

  @Override
  public boolean exists(Date pStartDate, Date pEndDate) {
    return getManager().exists(pStartDate, pEndDate);
  }
}
