package org.ums.common.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.common.Resource;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;
import org.ums.persistent.model.PersistentBearerAccessToken;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.UUID;

@Component
@Path("/login")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class UMSLogin {

  @Autowired
  BearerAccessTokenManager mBearerAccessTokenManager;

  @GET
  @Transactional
  public JsonObject login() throws Exception {
    Subject currentUser = SecurityUtils.getSubject();
    String userName = currentUser.getPrincipal().toString();
    final JsonObjectBuilder builder = Json.createObjectBuilder();
    builder.add("userId", userName);
    builder.add("userName", userName);

    mBearerAccessTokenManager.invalidateTokens(userName);
    String newToken = UUID.randomUUID().toString();
    MutableBearerAccessToken accessToken = new PersistentBearerAccessToken();
    accessToken.setUserId(userName);
    accessToken.setId(newToken);
    accessToken.commit(false);

    builder.add("token", newToken);

    return builder.build();
  }

}
