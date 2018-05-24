package org.ums.accounts.resource.definitions.financial.account.year;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.domain.model.mutable.accounts.MutableFinancialAccountYear;
import org.ums.enums.accounts.definitions.financial.account.year.BookClosingFlagType;
import org.ums.enums.accounts.definitions.financial.account.year.FinancialAccountYearTransferType;
import org.ums.enums.accounts.definitions.financial.account.year.YearClosingFlagType;
import org.ums.exceptions.ValidationException;
import org.ums.manager.accounts.AccountBalanceManager;
import org.ums.manager.accounts.FinancialAccountYearManager;
import org.ums.resource.ResourceHelper;
import org.ums.resource.helper.UserResourceHelper;
import org.ums.service.AccountBalanceService;
import org.ums.service.AccountService;
import org.ums.service.FinancialYearService;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
@Component
public class FinancialAccountYearResourceHelper extends
    ResourceHelper<FinancialAccountYear, MutableFinancialAccountYear, Long> {

  @Autowired
  private FinancialAccountYearManager mFinancialAccountYearManager;
  @Autowired
  private UserManager mUserManager;
  @Autowired
  private FinancialYearService mFinancialYearService;
  @Autowired
  private AccountBalanceService mAccountBalanceService;
  @Autowired
  private AccountBalanceManager mAccountBalanceManager;
  @Autowired
  private UserResourceHelper mUserResourceHelper;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Transactional
  protected List<FinancialAccountYear> updateFinancialAccountYear(
      MutableFinancialAccountYear pMutableFinancialAccountYear) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    pMutableFinancialAccountYear.setModifiedDate(new Date());
    pMutableFinancialAccountYear.setModifiedBy(user.getEmployeeId());
    if(pMutableFinancialAccountYear.getStringId() == null) {
      if(mFinancialAccountYearManager.exists(pMutableFinancialAccountYear.getCurrentStartDate(),
          pMutableFinancialAccountYear.getCurrentEndDate()))
        throw new ValidationException("Date is overlapping");
      pMutableFinancialAccountYear.setYearClosingFlag(YearClosingFlagType.OPEN);
      pMutableFinancialAccountYear.setBookClosingFlag(BookClosingFlagType.OPEN);
      getContentManager().create(pMutableFinancialAccountYear);
    }
    else
      getContentManager().update(pMutableFinancialAccountYear);
    return getContentManager().getAll();
  }

  @Transactional
  public void closeAndCreateNewFinancialAccountYear(final Date startDate, final Date endDate,
      final FinancialAccountYearTransferType pFinancialAccountYearTransferType) {

    MutableFinancialAccountYear financialAccountYear = mFinancialAccountYearManager.getOpenedFinancialAccountYear();
    User modifer = mUserResourceHelper.getLoggedUser();
    MutableFinancialAccountYear newFinancialAccountYear =
        mFinancialYearService.createNewFinancialAccountYear(startDate, endDate, financialAccountYear);

    List<MutableAccountBalance> transferredAccountBalanceList = new ArrayList<>();

    if(pFinancialAccountYearTransferType.equals(FinancialAccountYearTransferType.ALL_ACCOUNTS_CLOSING)) {
      transferredAccountBalanceList =
          mAccountBalanceService.transferAccountBalanceToNewAcademicYear(newFinancialAccountYear);
      financialAccountYear.setYearClosingFlag(YearClosingFlagType.CLOSED);
      financialAccountYear.setBookClosingFlag(BookClosingFlagType.CLOSED);
      financialAccountYear.setModifiedBy(modifer.getEmployeeId());
      financialAccountYear.setModifiedDate(new Date());
    }

    mFinancialAccountYearManager.update(financialAccountYear);
    mFinancialAccountYearManager.create(newFinancialAccountYear);
    mAccountBalanceManager.create(transferredAccountBalanceList);

  }

  @Override
  protected FinancialAccountYearManager getContentManager() {
    return mFinancialAccountYearManager;
  }

  @Override
  protected Builder<FinancialAccountYear, MutableFinancialAccountYear> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(FinancialAccountYear pReadonly) {
    return null;
  }
}
