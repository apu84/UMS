package org.ums.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ums.accounts.resource.definitions.account.AccountResourceHelper;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.enums.accounts.definitions.account.balance.AccountType;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.enums.accounts.definitions.financial.account.year.YearClosingFlagType;
import org.ums.enums.accounts.definitions.group.GroupType;
import org.ums.generator.IdGenerator;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountBalanceManager;
import org.ums.manager.accounts.AccountManager;
import org.ums.manager.accounts.FinancialAccountYearManager;
import org.ums.mapper.account.AccountBalanceMapper;
import org.ums.persistent.model.accounts.PersistentAccount;
import org.ums.persistent.model.accounts.PersistentAccountBalance;
import org.ums.resource.helper.UserResourceHelper;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class AccountService {
  @Autowired
  private CompanyManager mCompanyManager;
  @Autowired
  private AccountManager mAccountManager;
  @Autowired
  private AccountBalanceManager mAccountBalanceManager;
  @Autowired
  private UserResourceHelper mUserResourceHelper;
  @Autowired
  private AccountBalanceService mAccountBalanceService;

  @Transactional
  public Account getOpeningBalanceAdjustmentAccount() {
    Company company = mCompanyManager.getDefaultCompany();
    Long openingBalanceAdjustmentId =
        AccountType.OPENING_BALANCE_ADJUSTMENT_ACCOUNT.getValue() + Long.parseLong(company.getId());
    Account account = new PersistentAccount();
    if(mAccountManager.exists(openingBalanceAdjustmentId))
      account = mAccountManager.get(openingBalanceAdjustmentId);
    if(account.getId() == null) {
      PersistentAccount openingBalanceAdjustmentAccount = new PersistentAccount();
      Long initialId = AccountType.OPENING_BALANCE_ADJUSTMENT_ACCOUNT.getValue();
      openingBalanceAdjustmentAccount.setId(initialId + Long.parseLong(company.getId()));
      openingBalanceAdjustmentAccount.setAccGroupCode(GroupType.LIABILITIES.getValue());
      openingBalanceAdjustmentAccount.setAccountName("OPENING BALANCE ADJUSTMENT ACCOUNT");
      openingBalanceAdjustmentAccount.setAccountCode(openingBalanceAdjustmentId);
      openingBalanceAdjustmentAccount.setCompanyId(company.getId());
      openingBalanceAdjustmentAccount.setModifiedBy(mUserResourceHelper.getLoggedUser().getEmployeeId());
      openingBalanceAdjustmentAccount.setModifiedDate(new Date());
      mAccountManager.create(openingBalanceAdjustmentAccount);
      PersistentAccountBalance accountBalance =
          AccountBalanceMapper.convertFromAccountToAccountBalance(openingBalanceAdjustmentAccount);
      mAccountBalanceService.createAccountBalance(openingBalanceAdjustmentAccount, mUserResourceHelper.getLoggedUser(),
          accountBalance);
      account = openingBalanceAdjustmentAccount;
    }
    return account;
  }

}
