/*
package org.ums.accounts.resource.definitions.account;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.UriInfo;

*/
/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 *//*

@Component
@Path("/account/definition/account")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AccountResource extends MutableAccountResource {

  @GET
  @Path("/total-size")
  public Integer getTotalSize() {
    return mHelper.getContentManager().getSize();
  }

  @GET
  @Path("/all")
  public JsonObject getAll(UriInfo pUriInfo) {
    return mHelper.getAll(pUriInfo);
  }

  @GET
  @Path("/paginated/item-per-page/{item-per-page}/page-number/{page-number}")
  public JsonObject getAllPaginated(@PathParam("item-per-page") int pItemPerPage,
      @PathParam("page-number") int pPageNumber, UriInfo pUriInfo) {
    return mHelper.getAllPaginated(pItemPerPage, pPageNumber, pUriInfo);
  }

  @GET
  @Path("search/account-name/{account-name}")
  public JsonObject getAccountsByAccountName(@PathParam("account-name") String pAccountName, UriInfo pUriInfo) {
    return mHelper.getAccountsByAccountName(pAccountName, pUriInfo);
  }

}
*/
