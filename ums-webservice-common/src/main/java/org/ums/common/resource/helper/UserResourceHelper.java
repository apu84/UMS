package org.ums.common.resource.helper;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.ResourceHelper;
import org.ums.common.builder.UserBuilder;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.immutable.User;
import org.ums.manager.UserManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class UserResourceHelper extends ResourceHelper<User, MutableUser, String> {
  @Autowired
  UserManager mUserManager;

  @Autowired
  UserBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
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
  protected String getEtag(User pReadonly) {
    return "";
  }

  public JsonObject getUsers(final UriInfo pUriInfo) throws Exception {
    List<User> users = mUserManager.getUsers();

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (User user : users) {
      children.add(toJson(user, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }
}
