package org.ums.accounts.resource.definitions.account.balance;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
@Component
public class AccountBalanceResourceHelper extends ResourceHelper<AccountBalance, MutableAccountBalance, Long> {
  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<AccountBalance, MutableAccountBalance, Long> getContentManager() {
    return null;
  }

  @Override
  protected Builder<AccountBalance, MutableAccountBalance> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(AccountBalance pReadonly) {
    return null;
  }
}
