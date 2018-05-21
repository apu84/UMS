package org.ums.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableFinancialAccountYear;
import org.ums.enums.accounts.definitions.financial.account.year.BookClosingFlagType;
import org.ums.enums.accounts.definitions.financial.account.year.YearClosingFlagType;
import org.ums.generator.IdGenerator;
import org.ums.manager.accounts.FinancialAccountYearManager;
import org.ums.persistent.model.accounts.PersistentFinancialAccountYear;

import java.util.Date;

@Service
public class FinancialYearService {

  @Autowired
  private IdGenerator mIdGenerator;

  public MutableFinancialAccountYear createNewFinancialAccountYear(final Date pStartDate, final Date pEndDate,
      final FinancialAccountYear pOpenedFinancialAccountYear) {
    MutableFinancialAccountYear financialAccountYear = new PersistentFinancialAccountYear();
    financialAccountYear.setId(mIdGenerator.getNumericId());
    financialAccountYear.setCurrentStartDate(pStartDate);
    financialAccountYear.setCurrentEndDate(pEndDate);
    financialAccountYear.setPreviousStartDate(pOpenedFinancialAccountYear.getCurrentStartDate());
    financialAccountYear.setPreviousEndDate(pOpenedFinancialAccountYear.getCurrentEndDate());
    financialAccountYear.setBookClosingFlag(BookClosingFlagType.OPEN);
    financialAccountYear.setYearClosingFlag(YearClosingFlagType.OPEN);
    return financialAccountYear;
  }
}
