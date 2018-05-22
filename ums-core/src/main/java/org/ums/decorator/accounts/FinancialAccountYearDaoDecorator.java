package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableFinancialAccountYear;
import org.ums.manager.accounts.FinancialAccountYearManager;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class FinancialAccountYearDaoDecorator extends
    ContentDaoDecorator<FinancialAccountYear, MutableFinancialAccountYear, Long, FinancialAccountYearManager> implements
    FinancialAccountYearManager {

  @Override
  public FinancialAccountYear getOpenedFinancialAccountYear() {
    return getManager().getOpenedFinancialAccountYear();
  }

  @Override
  public void deleteAll() {
    getManager().deleteAll();
  }
}
