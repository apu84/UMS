package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.helper.AbsLateComingInfoResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
public class MutableAbsLateComingInfoResource extends Resource {
  @Autowired
  AbsLateComingInfoResourceHelper mHelper;

  @POST
  @Path("/addRecords")
  @Produces({MediaType.APPLICATION_JSON})
  public Response addExpelRecords(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }
}
