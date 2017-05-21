package org.ums.resource.leavemanagement;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Monjur-E-Morshed on 08-May-17.
 */
@Component
@Path("/lmsType")
public class LmsTypeResource extends MutableLmsTypeResource {

  @GET
  @Path("/all")
  public JsonObject getAllLmsTypeOfEmployee(final @Context Request pRequest) throws Exception {
    return mHelper.getAllLeaveTypes(mUriInfo);
  }

}
