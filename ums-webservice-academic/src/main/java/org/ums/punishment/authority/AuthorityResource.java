package org.ums.punishment.authority;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("authority")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AuthorityResource extends MutableAuthorityResource {

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mHelper.getAll(mUriInfo);
  }
}
