package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.PeriodClose;
import org.ums.domain.model.mutable.accounts.MutablePeriodClose;
import org.ums.manager.accounts.PeriodCloseManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
public class PeriodCloseDaoDecorator extends
    ContentDaoDecorator<PeriodClose, MutablePeriodClose, Long, PeriodCloseManager> implements PeriodCloseManager {

  @Override
  public List<PeriodClose> getByCurrentYear(Company pCompany) {
    return getManager().getByCurrentYear(pCompany);
  }

  @Override
  public List<PeriodClose> getByPreviousYear(Company pCompany) {
    return getManager().getByPreviousYear(pCompany);
  }

  @Override
  public boolean checkWhetherCurrentOpenedYearExists(Company pCompany) {
    return getManager().checkWhetherCurrentOpenedYearExists(pCompany);
  }
}
