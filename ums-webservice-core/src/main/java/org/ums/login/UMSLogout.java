package org.ums.login;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.manager.BearerAccessTokenManager;

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
  private static final Logger mLogger = LoggerFactory.getLogger(UMSLogout.class);

  @Autowired
  BearerAccessTokenManager mBearerAccessTokenManager;

  @GET
  public Response logout() throws Exception {
    SecurityUtils.getSubject().logout();
    return Response.ok().build();
  }
}
