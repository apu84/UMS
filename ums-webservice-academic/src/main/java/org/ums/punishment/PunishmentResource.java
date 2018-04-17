package org.ums.punishment;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("punishment")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class PunishmentResource extends MutablePunishmentResource {

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mHelper.getAll(mUriInfo);
  }
}
