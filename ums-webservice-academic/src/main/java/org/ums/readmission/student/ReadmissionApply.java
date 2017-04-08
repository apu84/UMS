package org.ums.readmission.student;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

@Component
@Path("/readmission")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ReadmissionApply {
  @GET
  @Path("/semester/{semesterId}")
  public JsonObject getReadmissionStatus(final @Context Request pRequest, final @PathParam("semesterId") int pSemesterId)
      throws Exception {
    return null;
  }

}
