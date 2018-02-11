package org.ums.resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.processor.userhome.UserInfo;
import org.ums.services.UserHomeService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import java.util.List;
import java.util.Map;

@Component
@Path("/userHome")
@Produces(Resource.MIME_TYPE_JSON)
public class UserHomeResource extends Resource {
  @Autowired
  private UserHomeService mUserHomeService;

  @GET
  public UserInfo get(final @Context Request pRequest) {
    return mUserHomeService.process(SecurityUtils.getSubject());
  }
}
