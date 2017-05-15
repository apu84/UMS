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
  @Path("/getPublicationInformation")
  public JsonObject getPublicationInformation(final @Context Request pRequest) throws Exception {
    return mPublicationInformationResourceHelper.getPublicationInformation(mUriInfo);
  }

  @GET
  @Path("/getPublicationInformation/{employee-id}")
  public JsonObject getPublicationInformation(final @PathParam("employee-id") String pEmployeeId,
      final @Context Request pRequest) throws Exception {
    return mPublicationInformationResourceHelper.getPublicationInformation(pEmployeeId, mUriInfo);
  }

  @GET
  @Path("/getTeachersList/{status}")
  public JsonObject getTeachersList(final @PathParam("status") String pStatus) {
    return mPublicationInformationResourceHelper.getTeachersList(pStatus, mUriInfo);
  }
}
