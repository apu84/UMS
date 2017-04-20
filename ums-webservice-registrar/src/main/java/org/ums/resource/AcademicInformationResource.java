package org.ums.resource;

import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.registrar.MutableExperienceInformation;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("registrar/employee/academic")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AcademicInformationResource extends MutableAcademicInformationResource {

  @GET
  @Path("/getAcademicInformation/{employee-id}")
  public JsonObject getAcademicInformation(final @Context Request pRequest,
      final @PathParam("employee-id") String pEmployeeId) throws Exception {
    return mAcademicInformationResourceHelper.getAcademicInformation(pEmployeeId, mUriInfo);
  }
}
