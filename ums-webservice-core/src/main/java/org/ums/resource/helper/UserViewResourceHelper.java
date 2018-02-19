package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.ums.builder.Builder;
import org.ums.builder.UserViewBuilder;
import org.ums.cache.LocalCache;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.userView.MutableUserView;
import org.ums.usermanagement.userView.PersistentUserView;
import org.ums.usermanagement.userView.UserView;
import org.ums.usermanagement.userView.UserViewManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class UserViewResourceHelper extends ResourceHelper<UserView, MutableUserView, String> {

  @Autowired
  UserViewManager mManager;

  @Autowired
  UserViewBuilder mBuilder;

  public JsonObject getUser(final String pUserId, final UriInfo pUriInfo) {
    UserView user = new PersistentUserView();
    user = mManager.get(pUserId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    LocalCache localCache = new LocalCache();
    object.add("entries", toJson(user, pUriInfo, localCache));
    return object.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<UserView, MutableUserView, String> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<UserView, MutableUserView> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(UserView pReadonly) {
    return null;
  }
}
