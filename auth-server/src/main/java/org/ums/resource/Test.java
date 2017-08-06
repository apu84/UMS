package org.ums.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;

@Component
@Path("/test")
@Produces("application/json")
@Consumes("application/json")
public class Test {
  @GET
  public Response logout() throws Exception {
    return Response.ok().build();
  }
}
