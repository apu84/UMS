package org.ums.manager.accounts;

import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.PeriodClose;
import org.ums.domain.model.mutable.accounts.MutablePeriodClose;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
public interface PeriodCloseManager extends ContentManager<PeriodClose, MutablePeriodClose, Long> {

  boolean checkWhetherCurrentOpenedYearExists(Company pCompany);

  List<PeriodClose> getByCurrentYear(Company pCompany);

  List<PeriodClose> getByPreviousYear(Company pCompany);

}
