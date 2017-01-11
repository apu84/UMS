package org.ums.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.ParameterManager;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("/academic/academicCalenderParameter")
public class ParameterResource extends MutableParameterResource {
  @Autowired
  ParameterManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mParameterResourceHelper.getAll(mUriInfo);
  }
}
