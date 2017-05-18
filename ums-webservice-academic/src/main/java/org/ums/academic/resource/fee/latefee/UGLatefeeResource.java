package org.ums.academic.resource.fee.latefee;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

@Component
@Path("/academic/ug/latefee")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class UGLatefeeResource extends Resource {
  @Autowired
  UGLatefeeResourceHelper mUGUgLatefeeResourceHelper;

  @GET
  @Path("/semester/{semester-id}")
  public JsonObject getLatefeeInfo(final @Context Request pRequest, final @PathParam("object-id") Integer pSemesterId)
      throws Exception {
    return mUGUgLatefeeResourceHelper.getLatefees(pSemesterId, mUriInfo);
  }

  @POST
  public Response create(final JsonObject pJsonObject) throws Exception {
    return mUGUgLatefeeResourceHelper.post(pJsonObject, mUriInfo);
  }

  @GET
  @Path("/semester/{semester-id}")
  public Response update(final JsonObject pJsonObject, final @PathParam("object-id") Integer pSemesterId)
      throws Exception {
    return mUGUgLatefeeResourceHelper.updateLatefees(pSemesterId, pJsonObject, mUriInfo);
  }
}
