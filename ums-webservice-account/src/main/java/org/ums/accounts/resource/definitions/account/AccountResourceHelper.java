package org.ums.accounts.resource.definitions.account;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.accounts.resource.definitions.account.balance.AccountBalanceBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.enums.accounts.definitions.financial.account.year.YearClosingFlagType;
import org.ums.enums.accounts.definitions.group.GroupFlag;
import org.ums.enums.accounts.definitions.group.GroupType;
import org.ums.generator.IdGenerator;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.*;
import org.ums.persistent.model.accounts.PersistentAccount;
import org.ums.persistent.model.accounts.PersistentAccountBalance;
import org.ums.resource.ResourceHelper;
import org.ums.service.AccountBalanceService;
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

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public List<Account> createAccount( PersistentAccount pAccount, PersistentAccountBalance pAccountBalance, int pItemPerPage, int pItemNumber) {
//    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache cache = new LocalCache();
    MutableAccount account = pAccount;
//    JsonObject jsonObject = entries.getJsonObject(0);
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    account.setModifiedBy(user.getEmployeeId());
    account.setModifiedDate(new Date());
    account.setAccountCode(account.getAccountCode() == null || account.getAccountCode().equals("") ? mIdGenerator.getNumericId() : account.getAccountCode());
    account.setCompanyId(mCompanyManager.getDefaultCompany().getId());
    Long id = getContentManager().create(account);
    account.setId(id);
    MutableAccountBalance accountBalance = pAccountBalance;
    if (accountBalance != null) {
      FinancialAccountYear financialAccountYears = mFinancialAccountYearManager.getAll().stream().filter(f -> f.getYearClosingFlag().equals(YearClosingFlagType.OPEN)).collect(Collectors.toList()).get(0);
      accountBalance.setId(mIdGenerator.getNumericId());
      accountBalance.setYearOpenBalanceType(BalanceType.Dr);
      accountBalance.setYearOpenBalance(accountBalance.getYearOpenBalance() == null ? new BigDecimal(0.00) : accountBalance.getYearOpenBalance());
      accountBalance.setFinStartDate(financialAccountYears.getCurrentStartDate());
      accountBalance.setFinEndDate(financialAccountYears.getCurrentEndDate());
      accountBalance.setAccountCode(id);
      accountBalance.setTotDebitTrans(accountBalance.getYearOpenBalance() == null ? new BigDecimal(0.00) : accountBalance.getYearOpenBalance());
      accountBalance.setTotCreditTrans(new BigDecimal(0.000));
      accountBalance.setModifiedBy(user.getEmployeeId());
      accountBalance.setModifiedDate(new Date());
      accountBalance = mAccountBalanceService.setMonthAccountBalance(accountBalance, account);
      mAccountBalanceManager.insertFromAccount(accountBalance);
    }

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

  public JsonObject getCustomerAndVendorAccounts(final UriInfo pUriInfo) {
    List<String> groupCodeList = new ArrayList<>();
    // groupCodeList.add(GroupType.SUNDRY_CREDITOR.getValue());
    groupCodeList.add(mSystemGroupMapManager.get(GroupType.SUNDRY_CREDITOR.getValue()).getGroup().getGroupCode());
    // groupCodeList.add(GroupType.SUNDRY_DEBTOR.getValue());
    groupCodeList.add(mSystemGroupMapManager.get(GroupType.SUNDRY_DEBTOR.getValue()).getGroup().getGroupCode());
    return getAccountsBasedOnGroupList(groupCodeList, pUriInfo);
  }

  public JsonObject getVendorAccounts(final UriInfo pUriInfo) {
    List<String> groupCodeList = new ArrayList<>();
    // groupCodeList.add(GroupType.SUNDRY_CREDITOR.getValue());
    groupCodeList.add(mSystemGroupMapManager.get(GroupType.SUNDRY_CREDITOR.getValue()).getGroup().getGroupCode());
    return getAccountsBasedOnGroupList(groupCodeList, pUriInfo);
  }

  public JsonObject getCustomerAccounts(final UriInfo pUriInfo) {
    List<String> groupCodeList = new ArrayList<>();
    // groupCodeList.add(GroupType.SUNDRY_DEBTOR.getValue());
    groupCodeList.add(mSystemGroupMapManager.get(GroupType.SUNDRY_DEBTOR.getValue()).getGroup().getGroupCode());
    return getAccountsBasedOnGroupList(groupCodeList, pUriInfo);
  }

  private JsonObject getAccountsBasedOnGroupList(List<String> pGroupCodeList, UriInfo pUriInfo) {
    List<Group> groups = mGroupManager.getIncludingMainGroupList(pGroupCodeList);
    List<Account> accounts = mAccountManager.getIncludingGroups(groups.stream().map(a -> a.getGroupCode()).collect(Collectors.toList()));
    return getJsonObject(pUriInfo, accounts);
  }

  private JsonObject getAccountExcludingGroupList(List<String> pGroupCodeList, UriInfo pUriInfo) {
    List<Group> groups = mGroupManager.getExcludingMainGroupList(pGroupCodeList);
    List<Account> accounts = mAccountManager.getIncludingGroups(groups.stream().map(a -> a.getGroupCode()).collect(Collectors.toList()));
    return getJsonObject(pUriInfo, accounts);
  }

  public JsonObject getBankAndCostTypeAccounts(final UriInfo pUriInfo) {
    List<String> groupCodeList = new ArrayList<String>();
    // groupCodeList.add(GroupType.BANK_ACCOUNTS.getValue());
    groupCodeList.add(mSystemGroupMapManager.get(GroupType.BANK_ACCOUNTS.getValue()).getGroup().getGroupCode());
    // groupCodeList.add(GroupType.CASH_IN_HAND.getValue());
    groupCodeList.add(mSystemGroupMapManager.get(GroupType.CASH_IN_HAND.getValue()).getGroup().getGroupCode());
    return getAccountsBasedOnGroupList(groupCodeList, pUriInfo);

  }

  public JsonObject getExcludingBankAndCostTypeAccounts(final UriInfo pUriInfo) {
    List<String> groupCodeList = new ArrayList<String>();
    // groupCodeList.add(GroupType.BANK_ACCOUNTS.getValue());
    groupCodeList.add(mSystemGroupMapManager.get(GroupType.BANK_ACCOUNTS.getValue()).getGroup().getGroupCode());
    // groupCodeList.add(GroupType.CASH_IN_HAND.getValue());
    groupCodeList.add(mSystemGroupMapManager.get(GroupType.CASH_IN_HAND.getValue()).getGroup().getGroupCode());
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
