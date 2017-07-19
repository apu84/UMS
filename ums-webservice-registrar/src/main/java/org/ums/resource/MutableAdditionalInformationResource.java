package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.registrar.AdditionalInformation;
import org.ums.resource.helper.AdditionalInformationResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableAdditionalInformationResource extends Resource {

  @Autowired
  AdditionalInformationResourceHelper mHelper;

  @POST
  @Path("/saveAdditionalInformation")
  public Response saveAdditionalInformation(final JsonObject pJsonObject) {
    return mHelper.saveAdditionalInformation(pJsonObject, mUriInfo);
  }
}
