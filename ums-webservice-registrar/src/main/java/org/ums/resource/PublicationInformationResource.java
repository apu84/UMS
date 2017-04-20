package org.ums.resource;

import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("registrar/employee/publication")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class PublicationInformationResource extends MutablePublicationInformationResource {

  @GET
  @Path("/getPublicationInformation/{employee-id}")
  public JsonObject getPublicationInformation(final @Context Request pRequest,
      final @PathParam("employee-id") String pEmployeeId) throws Exception {
    return mPublicationInformationResourceHelper.getPublicationInformation(pEmployeeId, mUriInfo);
  }
}
