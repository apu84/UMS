package org.ums.common.login;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Component
@Path("/logout")
@Consumes(Resource.MIME_TYPE_JSON)
@Produces(Resource.MIME_TYPE_JSON)
public class UMSLogout {
  @GET
  public Response logout() throws Exception {
    SecurityUtils.getSubject().logout();
    return Response.ok().build();
  }
}
