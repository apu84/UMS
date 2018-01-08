package org.ums.accounts.resource.definitions.account;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.accounts.resource.definitions.account.balance.AccountBalanceBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.enums.accounts.definitions.financial.account.year.YearClosingFlagType;
import org.ums.generator.IdGenerator;
import org.ums.manager.accounts.AccountBalanceManager;
import org.ums.manager.accounts.AccountManager;
import org.ums.manager.accounts.FinancialAccountYearManager;
import org.ums.persistent.model.accounts.PersistentAccount;
import org.ums.persistent.model.accounts.PersistentAccountBalance;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public JsonObject createAccount(final JsonObject pJsonObject, int pItemPerPage, int pItemNumber,
      final UriInfo pUriInfo) {
//    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache cache = new LocalCache();
    MutableAccount account = new PersistentAccount();
//    JsonObject jsonObject = entries.getJsonObject(0);
    getBuilder().build(account, pJsonObject, cache);
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    account.setModifiedBy(user.getEmployeeId());
    account.setModifiedDate(new Date());
    account.setAccountCode(mIdGenerator.getNumericId());
    Long id = getContentManager().create(account);
    account.setId(id);
    MutableAccountBalance accountBalance = new PersistentAccountBalance();
    mAccountBalanceBuilder.build(accountBalance, pJsonObject, cache);
    if (accountBalance != null) {
      FinancialAccountYear financialAccountYears = mFinancialAccountYearManager.getAll().stream().filter(f -> f.getYearClosingFlag().equals(YearClosingFlagType.OPEN)).collect(Collectors.toList()).get(0);
      accountBalance.setFinStartDate(financialAccountYears.getCurrentStartDate());
      accountBalance.setFinEndDate(financialAccountYears.getCurrentEndDate());
      accountBalance.setAccountCode(id);
      accountBalance.setModifiedBy(user.getEmployeeId());
      accountBalance.setModifiedDate(new Date());
      mAccountBalanceManager.insertFromAccount(accountBalance);
    }

    return getAllPaginated(pItemPerPage, pItemNumber, pUriInfo);
  }

  public JsonObject getAll(final UriInfo pUriInfo) {
    List<Account> accounts = getContentManager().getAll();
    return getJsonObject(pUriInfo, accounts);
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

  public JsonObject getAllPaginated(final int pItemPerPage, final int pPageNumber, final UriInfo pUriInfo) {
    List<Account> accounts = getContentManager().getAllPaginated(pItemPerPage, pPageNumber);
    return getJsonObject(pUriInfo, accounts);
  }

  public JsonObject getAccountsByAccountName(final String pAccountName, final UriInfo pUriInfo) {
    List<Account> accounts = getContentManager().getAccounts(pAccountName);
    return getJsonObject(pUriInfo, accounts);
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
