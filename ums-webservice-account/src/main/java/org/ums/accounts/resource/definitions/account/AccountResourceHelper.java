package org.ums.accounts.resource.definitions.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.generator.IdGenerator;
import org.ums.manager.accounts.AccountManager;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
  IdGenerator mIdGenerator;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected AccountManager getContentManager() {
    return mAccountManager;
  }

  @Override
  protected Builder<Account, MutableAccount> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(Account pReadonly) {
    return null;
  }
}
