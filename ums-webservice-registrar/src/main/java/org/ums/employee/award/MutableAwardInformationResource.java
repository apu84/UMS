package org.ums.employee.award;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.employee.award.AwardInformationResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutableAwardInformationResource extends Resource {

  @Autowired
  AwardInformationResourceHelper mAwardInformationResourceHelper;

  @POST
  @Path("/save")
  public Response saveServiceInformation(final JsonObject pJsonObject) {
    return mAwardInformationResourceHelper.saveAwardInformation(pJsonObject, mUriInfo);
  }
}
