package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.TrainingInformationResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableTrainingInformationResource extends Resource {

  @Autowired
  TrainingInformationResourceHelper mTrainingInformationResourceHelper;

  @POST
  @Path("/saveTrainingInformation")
  public Response saveServiceInformation(final JsonObject pJsonObject) {
    return mTrainingInformationResourceHelper.saveTrainingInformation(pJsonObject, mUriInfo);
  }
}
