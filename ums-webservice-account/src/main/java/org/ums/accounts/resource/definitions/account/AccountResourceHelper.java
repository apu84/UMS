package org.ums.accounts.resource.definitions.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.accounts.resource.definitions.account.balance.AccountBalanceBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.generator.IdGenerator;
import org.ums.manager.accounts.AccountBalanceManager;
import org.ums.manager.accounts.AccountManager;
import org.ums.persistent.model.accounts.PersistentAccount;
import org.ums.persistent.model.accounts.PersistentAccountBalance;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

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

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public JsonObject createAccount(final JsonObject pJsonObject, int pItemPerPage, int pItemNumber,
      final UriInfo pUriInfo) {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache cache = new LocalCache();
    MutableAccount account = new PersistentAccount();
    JsonObject jsonObject = entries.getJsonObject(0);
    getBuilder().build(account, jsonObject, cache);

    Long id = getContentManager().create(account);
    account.setId(id);
    MutableAccountBalance accountBalance = new PersistentAccountBalance();
    mAccountBalanceBuilder.build(accountBalance, jsonObject, cache);
    mAccountBalanceManager.insertFromAccount(accountBalance);
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
