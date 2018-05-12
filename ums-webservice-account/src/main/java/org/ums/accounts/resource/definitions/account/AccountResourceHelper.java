package org.ums.accounts.resource.definitions.account;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.accounts.resource.definitions.account.balance.AccountBalanceBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.enums.accounts.definitions.group.GroupFlag;
import org.ums.enums.accounts.definitions.group.GroupType;
import org.ums.enums.accounts.definitions.voucher.number.control.VoucherType;
import org.ums.exceptions.ValidationException;
import org.ums.generator.IdGenerator;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.*;
import org.ums.persistent.model.accounts.PersistentAccount;
import org.ums.persistent.model.accounts.PersistentAccountBalance;
import org.ums.resource.ResourceHelper;
import org.ums.service.AccountBalanceService;
import org.ums.service.AccountService;
import org.ums.service.AccountTransactionService;
import org.ums.service.VoucherService;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
@Component
public class AccountResourceHelper extends ResourceHelper<Account, MutableAccount, Long> {

  @Autowired
  private AccountManager mAccountManager;
  @Autowired
  private UserManager mUserManager;
  @Autowired
  private IdGenerator mIdGenerator;
  @Autowired
  private AccountBuilder mAccountBuilder;
  @Autowired
  private AccountBalanceManager mAccountBalanceManager;
  @Autowired
  private AccountBalanceBuilder mAccountBalanceBuilder;
  @Autowired
  private FinancialAccountYearManager mFinancialAccountYearManager;
  @Autowired
  private MonthManager mMonthManager;
  @Autowired
  private MonthBalanceManager mMonthBalanceManager;
  @Autowired
  private GroupManager mGroupManager;
  @Autowired
  private AccountBalanceService mAccountBalanceService;
  @Autowired
  private SystemGroupMapManager mSystemGroupMapManager;
  @Autowired
  private CompanyManager mCompanyManager;
  @Autowired
  private AccountService mAccountService;
  @Autowired
  private AccountTransactionService mAccountTransactionService;
  @Autowired
  private VoucherService mVoucherService;
  @Autowired
  private VoucherManager mVoucherManager;
  @Autowired
  private VoucherNumberControlManager mVoucherNumberControlManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public List<Account> createAccount(PersistentAccount pAccount, PersistentAccountBalance pAccountBalance,
      int pItemPerPage, int pItemNumber) throws Exception {
    MutableAccount account = pAccount;
    if(mVoucherNumberControlManager.getByVoucher(mVoucherManager.get(VoucherType.JOURNAL_VOUCHER.getId()),
        mCompanyManager.getDefaultCompany()).getVoucherLimit() == null) {
      throw new ValidationException("No limit for Journal Voucher, please set up the total limit.");
    }
    if(!mVoucherService.checkWhetherTheBalanceExceedsVoucherLimit(VoucherType.JOURNAL_VOUCHER,
        pAccountBalance.getYearOpenBalance())) {
      throw new ValidationException("Total limit exceeds for journal voucher");
    }

    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    account.setModifiedBy(user.getEmployeeId());
    account.setModifiedDate(new Date());
    account.setReserved(account.getReserved() == null ? false : account.getReserved());
    account.setAccountCode(account.getAccountCode() == null || account.getAccountCode().equals("") ? mIdGenerator
        .getNumericId() : account.getAccountCode());
    account.setCompanyId(mCompanyManager.getDefaultCompany().getId());
    if(account.getId() == null) {
      Long id = getContentManager().create(account);
      account.setId(id);
    }
    else {
      getContentManager().update(account);
      return getAllPaginated(pItemPerPage, pItemNumber);
    }

    MutableAccountBalance accountBalance = pAccountBalance;
    accountBalance = mAccountBalanceService.createAccountBalance(account, user, accountBalance);

    if(accountBalance.getYearOpenBalance().equals(new BigDecimal(0)) == false)
      mAccountTransactionService.createOpeningBalanceJournalEntry(account, accountBalance);
    return getAllPaginated(pItemPerPage, pItemNumber);
  }

  public List<Account> getAll() {
    List<Account> accounts = getContentManager().getAll();
    return accounts;
  }

  public JsonObject getAccounts(final GroupFlag pGroupFlag, final UriInfo pUriInfo) {
    List<Account> accounts = getContentManager().getAccounts(pGroupFlag);
    return getJsonObject(pUriInfo, accounts);
  }

  public List<Account> getCustomerAndVendorAccounts(final UriInfo pUriInfo) {
    List<String> groupCodeList = new ArrayList<>();
    if(mSystemGroupMapManager.exists(GroupType.SUNDRY_CREDITOR, mCompanyManager.getDefaultCompany()))
      groupCodeList.add(mSystemGroupMapManager.get(GroupType.SUNDRY_CREDITOR, mCompanyManager.getDefaultCompany())
          .getGroup().getGroupCode());
    if(mSystemGroupMapManager.exists(GroupType.SUNDRY_DEBTOR, mCompanyManager.getDefaultCompany()))
      groupCodeList.add(mSystemGroupMapManager.get(GroupType.SUNDRY_DEBTOR, mCompanyManager.getDefaultCompany())
          .getGroup().getGroupCode());
    return getAccountsBasedOnGroupList(groupCodeList, pUriInfo);
  }

  public List<Account> getVendorAccounts(final UriInfo pUriInfo) {
    List<String> groupCodeList = new ArrayList<>();
    if(mSystemGroupMapManager.exists(GroupType.SUNDRY_CREDITOR, mCompanyManager.getDefaultCompany()))
      groupCodeList.add(mSystemGroupMapManager.get(GroupType.SUNDRY_CREDITOR, mCompanyManager.getDefaultCompany())
          .getGroup().getGroupCode());
    return getAccountsBasedOnGroupList(groupCodeList, pUriInfo);
  }

  public List<Account> getCustomerAccounts(final UriInfo pUriInfo) {
    List<String> groupCodeList = new ArrayList<>();
    // groupCodeList.add(GroupType.SUNDRY_DEBTOR.getValue());

    if(mSystemGroupMapManager.exists(GroupType.SUNDRY_DEBTOR, mCompanyManager.getDefaultCompany()))
      groupCodeList.add(mSystemGroupMapManager.get(GroupType.SUNDRY_DEBTOR, mCompanyManager.getDefaultCompany())
          .getGroup().getGroupCode());
    return getAccountsBasedOnGroupList(groupCodeList, pUriInfo);
  }

  private List<Account> getAccountsBasedOnGroupList(List<String> pGroupCodeList, UriInfo pUriInfo) {
    List<Group> groups = mGroupManager.getIncludingMainGroupList(pGroupCodeList);
    List<Account> accounts = new ArrayList<>();
    if(groups!=null && groups.size()>0)
    accounts = mAccountManager.getIncludingGroups(groups.stream().map(a -> a.getGroupCode()).collect(Collectors.toList()));
    return accounts;
  }

  private List<Account> getAccountExcludingGroupList(List<String> pGroupCodeList, UriInfo pUriInfo) {
    List<Group> groups = mGroupManager.getExcludingMainGroupList(pGroupCodeList);
    List<Account> accounts = new ArrayList<>();
    if(groups!=null && groups.size()>0)
    accounts = mAccountManager.getIncludingGroups(groups.stream().map(a -> a.getGroupCode()).collect(Collectors.toList()));
    return accounts;
  }

  public List<Account> getBankAndCostTypeAccounts(final UriInfo pUriInfo) {
    List<String> groupCodeList = new ArrayList<String>();
    if(mSystemGroupMapManager.exists(GroupType.BANK_ACCOUNTS, mCompanyManager.getDefaultCompany()))
      groupCodeList.add(mSystemGroupMapManager
          .get(GroupType.SUNDRY_DEBTOR.BANK_ACCOUNTS, mCompanyManager.getDefaultCompany()).getGroup().getGroupCode());
    if(mSystemGroupMapManager.exists(GroupType.CASH_IN_HAND, mCompanyManager.getDefaultCompany()))
      groupCodeList.add(mSystemGroupMapManager.get(GroupType.CASH_IN_HAND, mCompanyManager.getDefaultCompany())
          .getGroup().getGroupCode());
    return getAccountsBasedOnGroupList(groupCodeList, pUriInfo);

  }

  public List<Account> getExcludingBankAndCostTypeAccounts(final UriInfo pUriInfo) {
    List<String> groupCodeList = new ArrayList<String>();
    if(mSystemGroupMapManager.exists(GroupType.BANK_ACCOUNTS, mCompanyManager.getDefaultCompany()))
      groupCodeList.add(mSystemGroupMapManager.get(GroupType.BANK_ACCOUNTS, mCompanyManager.getDefaultCompany())
          .getGroup().getGroupCode());
    if(mSystemGroupMapManager.exists(GroupType.CASH_IN_HAND, mCompanyManager.getDefaultCompany()))
      groupCodeList.add(mSystemGroupMapManager.get(GroupType.CASH_IN_HAND, mCompanyManager.getDefaultCompany())
          .getGroup().getGroupCode());
    return getAccountExcludingGroupList(groupCodeList, pUriInfo);
  }

  private JsonObject getJsonObject(UriInfo pUriInfo, List<Account> pAccounts) {
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    pAccounts.forEach(a -> {
      children.add(toJson(a, pUriInfo, localCache));
    });
    objectBuilder.add("entries", children);
    localCache.invalidate();
    return objectBuilder.build();
  }

  public List<Account> getAllPaginated(final int pItemPerPage, final int pPageNumber) {
    List<Account> accounts = getContentManager().getAllPaginated(pItemPerPage, pPageNumber);
    return accounts;
  }

  public List<Account> getAccountsByAccountName(final String pAccountName) {
    List<Account> accounts = getContentManager().getAccounts(pAccountName);
    return accounts;
  }

  private Integer generateSecureAccountId() {
    SecureRandom secureRandom = new SecureRandom();
    return secureRandom.nextInt(8);
  }

  @Override
  protected AccountManager getContentManager() {
    return mAccountManager;
  }

  @Override
  protected Builder<Account, MutableAccount> getBuilder() {
    return mAccountBuilder;
  }

  @Override
  protected String getETag(Account pReadonly) {
    return null;
  }
}
