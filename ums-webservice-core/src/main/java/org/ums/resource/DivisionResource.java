package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.resource.helper.DivisionResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("division")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class DivisionResource extends MutableDivisionResource {

  @Autowired
  DivisionResourceHelper mDivisionResourceHelper;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mDivisionResourceHelper.getAll(mUriInfo);
  }
}
