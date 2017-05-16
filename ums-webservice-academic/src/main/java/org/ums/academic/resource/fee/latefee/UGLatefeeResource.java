package org.ums.academic.resource.fee.latefee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("/academic/ug/latefee")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class UGLatefeeResource extends Resource {
  @Autowired
  UGLatefeeResourceHelper mUGUgLatefeeResourceHelper;

  @GET
  @Path("/semester" + PATH_PARAM_OBJECT_ID)
  public JsonObject getLatefeeInfo(final @Context Request pRequest, final @PathParam("object-id") Integer pSemesterId) {
    return null;
  }
}
