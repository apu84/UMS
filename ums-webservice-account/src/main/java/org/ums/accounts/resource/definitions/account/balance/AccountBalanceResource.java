package org.ums.accounts.resource.definitions.account.balance;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Monjur-E-Morshed on 31-Dec-17.
 */
@Component
@Path("/account/definition/account-balance")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AccountBalanceResource extends MutableAccountBalanceResource {

}
