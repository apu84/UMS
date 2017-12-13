package org.ums.employee.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class MutablePersonalInformationResource extends Resource {

  @Autowired
  PersonalInformationResourceHelper mHelper;

  @POST
  @Path("/save")
  public Response savePersonalInformation(final JsonObject pJsonObject) throws Exception {
    return mHelper.savePersonalInformation(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/update")
  public Response updatePersonalInformation(final JsonObject pJsonObject) {
    return mHelper.updatePersonalInformation(pJsonObject, mUriInfo);
  }
}
