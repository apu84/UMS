package org.ums.resource;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("registrar/employee/award")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AwardInformationResource extends MutableAwardInformationResource {

  @GET
  @Path("/getAwardInformation")
  public JsonObject getAwardInformation(final @Context Request pRequest) throws Exception {
    return mAwardInformationResourceHelper.getAwardInformation(mUriInfo);
  }
}