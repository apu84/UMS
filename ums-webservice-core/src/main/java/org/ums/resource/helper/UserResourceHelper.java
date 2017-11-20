package org.ums.resource.helper;

import org.apache.commons.lang.NotImplementedException;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.UserBuilder;
import org.ums.cache.LocalCache;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class UserResourceHelper extends ResourceHelper<User, MutableUser, String> {
  @Autowired
  public UserManager mUserManager;

  @Autowired
  UserBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    // Do nothing
    throw new NotImplementedException("Post method not implemented for UserResourceHelper");
  }

  @Override
  protected UserManager getContentManager() {
    return mUserManager;
  }

  @Override
  protected UserBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(User pReadonly) {
    return "";
  }

  public User getLoggedUser() {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    return user;
  }

  public JsonObject getUser(final String pUserId, final UriInfo pUriInfo) {
    User user = mUserManager.get(pUserId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    children.add(toJson(user, pUriInfo, localCache));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getUsers(final UriInfo pUriInfo) {
    List<User> users = mUserManager.getUsers();

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(User user : users) {
      children.add(toJson(user, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }
}
