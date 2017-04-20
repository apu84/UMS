package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.helper.ThanaResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("thana")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ThanaResource extends MutableThanaResource {

  @Autowired
  ThanaResourceHelper mThanaResourceHelper;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mThanaResourceHelper.getAll(mUriInfo);
  }
}
