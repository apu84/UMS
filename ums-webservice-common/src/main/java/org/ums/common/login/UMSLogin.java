package org.ums.common.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("/login")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class UMSLogin {

  @GET
  public JsonObject login() throws Exception {
    Subject currentUser = SecurityUtils.getSubject();
    String userName = currentUser.getPrincipal().toString();
    final JsonObjectBuilder builder = Json.createObjectBuilder();
    builder.add("userId", userName);
    builder.add("userName", userName);
    return builder.build();
  }

}
