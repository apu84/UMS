package org.ums.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.enums.accounts.definitions.group.GroupType;
import org.ums.exceptions.ValidationException;
import org.ums.generator.IdGenerator;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountBalanceManager;
import org.ums.manager.accounts.AccountManager;
import org.ums.manager.accounts.SystemGroupMapManager;
import org.ums.mapper.account.AccountBalanceMapper;
import org.ums.persistent.model.accounts.PersistentAccount;
import org.ums.persistent.model.accounts.PersistentAccountBalance;
import org.ums.resource.helper.UserResourceHelper;
import org.ums.util.Utils;

import java.util.Date;

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
  @Autowired
  private IdGenerator mIdGenerator;
  @Autowired
  private SystemGroupMapManager mSystemGroupManager;

  @Transactional
  public Account getOpeningBalanceAdjustmentAccount() {
    Company company = mCompanyManager.getDefaultCompany();
    Long openingBalanceAdjustmentAccountCode = Utils.OPENING_BALANCE_ADJUSTMENT_ACCOUNT_CODE;
    Account account = new PersistentAccount();
    if(mAccountManager.exists(openingBalanceAdjustmentAccountCode, company))
      account = mAccountManager.getAccount(openingBalanceAdjustmentAccountCode, company);
    if(account.getId() == null) {
      PersistentAccount openingBalanceAdjustmentAccount = new PersistentAccount();
      openingBalanceAdjustmentAccount.setId(mIdGenerator.getNumericId());
      if(!mSystemGroupManager.exists(GroupType.CURRENT_LIABILITIES, mCompanyManager.getDefaultCompany()))
        new ValidationException("Current Liabilities is not assigned in the System Group Map");
      else
        openingBalanceAdjustmentAccount.setAccGroupCode(mSystemGroupManager
            .get(GroupType.CURRENT_LIABILITIES, mCompanyManager.getDefaultCompany()).getGroup().getGroupCode());
      openingBalanceAdjustmentAccount.setAccountName("OPENING BALANCE ADJUSTMENT ACCOUNT");
      openingBalanceAdjustmentAccount.setAccountCode(openingBalanceAdjustmentAccountCode);
      openingBalanceAdjustmentAccount.setCompanyId(company.getId());
      openingBalanceAdjustmentAccount.setModifiedBy(mUserResourceHelper.getLoggedUser().getEmployeeId());
      openingBalanceAdjustmentAccount.setReserved(true);
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

  @Transactional
  public Account getRetailEarningsAccount() {

    Company company = mCompanyManager.getDefaultCompany();

    if(mAccountManager.exists(Utils.RETAIL_EARNINGS_ACCOUNT_CODE, company)) {
      return mAccountManager.getAccount(Utils.RETAIL_EARNINGS_ACCOUNT_CODE, company);
    }
    else {

      PersistentAccount mutableAccount = new PersistentAccount();
      mutableAccount.setId(mIdGenerator.getNumericId());
      mutableAccount.setAccountCode(Utils.RETAIL_EARNINGS_ACCOUNT_CODE);
      if(!mSystemGroupManager.exists(GroupType.CURRENT_LIABILITIES, company))
        new ValidationException("Current Liabilities is not assigned in the System Group Map");
      mutableAccount.setAccGroupCode(mSystemGroupManager.get(GroupType.CURRENT_LIABILITIES, company).getGroup()
          .getGroupCode());
      mutableAccount.setCompanyId(company.getId());
      mutableAccount.setAccountName("RETAIL EARNINGS");
      mutableAccount.setModifiedBy(mUserResourceHelper.getLoggedUser().getEmployeeId());
      mutableAccount.setReserved(true);
      mutableAccount.setModifiedDate(new Date());
      mAccountManager.create(mutableAccount);
      PersistentAccountBalance accountBalance = AccountBalanceMapper.convertFromAccountToAccountBalance(mutableAccount);
      mAccountBalanceService.createAccountBalance(mutableAccount, mUserResourceHelper.getLoggedUser(), accountBalance);
      return mutableAccount;
    }
  }

}
