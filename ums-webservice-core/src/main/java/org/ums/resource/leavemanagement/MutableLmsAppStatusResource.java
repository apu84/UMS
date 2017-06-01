package org.ums.resource.leavemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 20-May-17.
 */
public class MutableLmsAppStatusResource extends Resource {

  @Autowired
  protected LmsAppStatusResourceHelper mHelper;

  @POST
  @Path("/save")
  public Response saveLeaveApplicationStatus(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }

}
