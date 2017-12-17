package org.ums.employee.additional;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.employee.additional.AdditionalInformationResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableAdditionalInformationResource extends Resource {

  @Autowired
  AdditionalInformationResourceHelper mHelper;

  @POST
  @Path("/save")
  public Response saveAdditionalInformation(final JsonObject pJsonObject) {
    return mHelper.saveAdditionalInformation(pJsonObject, mUriInfo);
  }
}
