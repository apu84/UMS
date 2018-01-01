package org.ums.accounts.resource.definitions.account;

import org.springframework.beans.factory.annotation.Autowired;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class MutableAccountResource {

  @Autowired
  AccountResourceHelper mHelper;

  @POST
  @Path("/create/item-per-page/{item-per-page}/item-number/{item-number}")
  public JsonObject createAccount(@PathParam("item-per-page") int pItemPerPage,
      @PathParam("item-number") int pItemNumber, final JsonObject pJsonObject, UriInfo pUriInfo) {
    return mHelper.createAccount(pJsonObject, pItemPerPage, pItemNumber, pUriInfo);
  }

}
