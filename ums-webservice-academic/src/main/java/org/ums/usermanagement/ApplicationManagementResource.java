package org.ums.usermanagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.persistent.model.PersistentBearerAccessToken;
import org.ums.resource.Resource;
import org.ums.token.Token;
import org.ums.token.TokenBuilder;
import org.ums.usermanagement.application.Application;
import org.ums.usermanagement.application.ApplicationManager;
import org.ums.usermanagement.application.PersistentApplication;

@Component
@Path("/applications")
@Produces(Resource.MIME_TYPE_JSON)
public class ApplicationManagementResource extends Resource {
  @Autowired
  ApplicationManager mApplicationManager;
  @Autowired
  TokenBuilder mTokenBuilder;

  @GET
  @Path("/all")
  @RequiresPermissions("application:management")
  public List<Application> getAllApplications() {
    return mApplicationManager.getAll();
  }

  @POST
  @RequiresPermissions("application:management")
  public List<Application> createApplication(final PersistentApplication pApplication) {
    mApplicationManager.create(pApplication);
    return mApplicationManager.getAll();
  }

  @POST
  @Path("/{id}/token")
  @RequiresPermissions("application:management")
  public Map<String, String> issueToken(final @PathParam("id") String pApplicationId) {
    Token accessToken = mTokenBuilder.accessToken(pApplicationId, 3600);
    Token refreshToken = mTokenBuilder.refreshToken(pApplicationId, 3600);
    persistToken(pApplicationId, accessToken, refreshToken);
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("accessToken", accessToken.getHash());
    tokenMap.put("refreshToken", refreshToken.getHash());
    return tokenMap;
  }

  private void persistToken(String pAppId, Token accessToken, Token refreshToken) {
    MutableBearerAccessToken token = new PersistentBearerAccessToken();
    token.setUserId(pAppId);
    token.setId(accessToken.getHash());
    token.setRefreshToken(refreshToken.getHash());
    token.create();
  }
}
