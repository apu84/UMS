package org.ums.accounts.resource.definitions.account.balance;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import java.math.BigDecimal;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */

@Component
@Path("/account/account-balance")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AccountBalanceResource extends MutableAccountBalanceResource {

  @GET
  @Path("/account-id/{account-id}")
  public BigDecimal getAccountBalance(HttpServletRequest pHttpServletRequest, @PathParam("account-id") String pAccountId) {
    return mHelper.getAccountBalance(pAccountId);
  }
}
