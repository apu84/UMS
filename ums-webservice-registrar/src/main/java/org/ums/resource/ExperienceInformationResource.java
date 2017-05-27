package org.ums.resource;

import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.registrar.MutableExperienceInformation;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employee/experience")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ExperienceInformationResource extends MutableExperienceInformationResource {

  @GET
  @Path("/getExperienceInformation")
  public JsonObject getExperienceInformation(final @Context Request pRequest) throws Exception {
    return mExperienceInformationResourceHelper.getExperienceInformation(mUriInfo);
  }
}
