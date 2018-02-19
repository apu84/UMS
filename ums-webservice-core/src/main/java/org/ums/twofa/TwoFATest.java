package org.ums.twofa;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

@Component
@Path("/two-fa-test")
@Consumes(Resource.MIME_TYPE_JSON)
@Produces(Resource.MIME_TYPE_JSON)
public class TwoFATest extends Resource {
  @POST
  @TwoFA
  public Response twoFATest(final @Context Request pRequest, final JsonObject pJsonObject) {
    return Response.ok(pJsonObject).build();
  }
}
