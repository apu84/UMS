package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.AwardInformationResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class MutableAwardInformationResource extends Resource {

  @Autowired
  AwardInformationResourceHelper mAwardInformationResourceHelper;

  @POST
  @Path("/saveAwardInformation")
  public Response saveServiceInformation(final JsonObject pJsonObject) {
    return mAwardInformationResourceHelper.saveAwardInformation(pJsonObject, mUriInfo);
  }
}
