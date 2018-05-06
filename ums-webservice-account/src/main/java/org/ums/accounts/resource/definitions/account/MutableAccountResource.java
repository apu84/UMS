package org.ums.accounts.resource.definitions.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.accounts.resource.definitions.account.helper.AccountBalanceResponse;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.logs.PostLog;
import org.ums.persistent.model.accounts.PersistentAccount;
import org.ums.persistent.model.accounts.PersistentAccountBalance;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class MutableAccountResource extends Resource {

  @Autowired
  protected AccountResourceHelper mHelper;

  @POST
  @Path("/create/item-per-page/{item-per-page}/page-number/{page-number}")
  public List<Account> createAccount(@PathParam("item-per-page") int pItemPerPage,
      @PathParam("page-number") int pItemNumber, AccountBalanceResponse pAccountBalanceResponse) throws Exception {
    return mHelper.createAccount(pAccountBalanceResponse.getAccount(), pAccountBalanceResponse.getAccountBalance(),
        pItemPerPage, pItemNumber);
  }

  @POST
  @Path("/create")
  @PostLog(message = "Requested for account creation")
  public Response createAccount(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }
}
