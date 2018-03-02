package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.ApplicationCCIResourceHelper;
import org.ums.academic.resource.helper.ApplicationTESResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 2/20/2018.
 */
public class MutableApplicationTESResource extends Resource {

  @Autowired
  ApplicationTESResourceHelper mHelper;

  @POST
  @Path("/saveTES")
  @Produces({MediaType.APPLICATION_JSON})
  public Response cciApprovalbank(final JsonObject pJsonObject) {
    return mHelper.saveToTes(pJsonObject, mUriInfo);
  }
}
