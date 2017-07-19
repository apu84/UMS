package org.ums.resource;

import com.sun.tracing.dtrace.ProviderAttributes;
import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.registrar.MutableAdditionalInformation;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employee/additional")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AdditionalInformationResource extends MutableAdditionalInformationResource {

  @GET
  @Path("/getAdditionalInformation/employeeId/{employee-id}")
  public JsonObject getAdditionalInformation(final @PathParam("employee-id") String pEmployeeId,
      final @Context Request pRequest) throws Exception {
    return mHelper.getAdditionalInformation(pEmployeeId, mUriInfo);
  }
}
