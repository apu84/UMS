package org.ums.employee.additional;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.employee.additional.AreaOfInterestInformationResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableAreaOfInterestInformationResource extends Resource {

  @Autowired
  AreaOfInterestInformationResourceHelper mResourceHelper;

  @POST
  @Path("/saveAreaOfInterestInformation")
  public Response saveAreaOfInformation(final JsonObject pJsonObject) {
    return mResourceHelper.saveAreaOfInterestInformation(pJsonObject, mUriInfo);
  }
}
