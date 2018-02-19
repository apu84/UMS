package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
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
  public List<PeriodClose> getByCurrentYear() {
    return getManager().getByCurrentYear();
  }

  @Override
  public List<PeriodClose> getByPreviousYear() {
    return getManager().getByPreviousYear();
  }

  @Override
  public boolean checkWhetherCurrentOpenedYearExists() {
    return getManager().checkWhetherCurrentOpenedYearExists();
  }
}
