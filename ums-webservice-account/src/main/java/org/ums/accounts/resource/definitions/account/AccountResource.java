package org.ums.accounts.resource.definitions.account;

import org.springframework.stereotype.Component;
import org.ums.enums.accounts.definitions.group.GroupFlag;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

/*
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */

@Component
@Path("account/definition/account")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AccountResource extends MutableAccountResource {

  @GET
  @Path("/total-size")
  public Integer getTotalSize(final @Context Request pRequest) {
    return mHelper.getContentManager().getSize();
  }

  @GET
  @Path("/all")
  public JsonObject getAll(final @Context Request pRequest, UriInfo pUriInfo) {
    return mHelper.getAll(pUriInfo);
  }

  @GET
  @Path("/paginated/item-per-page/{item-per-page}/page-number/{page-number}")
  public JsonObject getAllPaginated(@PathParam("item-per-page") int pItemPerPage,
                                    @PathParam("page-number") int pPageNumber, final @Context Request pRequest) {
    return mHelper.getAllPaginated(pItemPerPage, pPageNumber, mUriInfo);
  }

  @GET
  @Path("search/account-name/{account-name}")
  public JsonObject getAccountsByAccountName(@PathParam("account-name") String pAccountName,
                                             final @Context Request pRequest) {
    return mHelper.getAccountsByAccountName(pAccountName, mUriInfo);
  }

  @GET
  @Path("/group-flag/{group-flag}")
  public JsonObject getAccountsByGroupFlag(@PathParam("group-flag") String pGroupFlag, final @Context Request pRequest) {
    return mHelper.getAccounts(GroupFlag.get(pGroupFlag), mUriInfo);
  }

  @GET
  @Path("/bank-cost-type-accounts")
  public JsonObject getBankAndCostTypeAccounts(final @Context HttpServletRequest pHttpServletRequest) {
    return mHelper.getBankAndCostTypeAccounts(mUriInfo);
  }

  @GET
  @Path("/excluding-bank-cost-type-accounts")
  public JsonObject getExcludingBankAndCostTypeAccounts(final @Context HttpServletRequest pHttpServletRequest) {
    return mHelper.getExcludingBankAndCostTypeAccounts(mUriInfo);
  }

  @GET
  @Path("/customer-vendor-accounts")
  public JsonObject getCustomerAndVendorAccounts(final @Context HttpServletRequest pHttpServletRequest)
      throws Exception {
    return mHelper.getCustomerAndVendorAccounts(mUriInfo);
  }

}
