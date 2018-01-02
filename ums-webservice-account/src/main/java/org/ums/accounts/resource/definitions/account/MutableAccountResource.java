package org.ums.accounts.resource.definitions.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class MutableAccountResource extends Resource {

  @Autowired
  protected AccountResourceHelper mHelper;

  @POST
  @Path("/create/item-per-page/{item-per-page}/page-number/{page-number}")
  public JsonObject createAccount(@PathParam("item-per-page") int pItemPerPage,
      @PathParam("page-number") int pItemNumber, final JsonObject pJsonObject, final @Context Request pRequest)
      throws Exception {
    return mHelper.createAccount(pJsonObject, pItemPerPage, pItemNumber, mUriInfo);
  }

  @POST
  @Path("/create")
  public Response createAccount(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }
}
