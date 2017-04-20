package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.helper.DistrictResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("district")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class DistrictResource extends MutableDistrictResource {

  @Autowired
  DistrictResourceHelper mDistrictResourceHelper;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mDistrictResourceHelper.getAll(mUriInfo);
  }
}
