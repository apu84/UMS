package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.FCMTokenBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.manager.ContentManager;
import org.ums.manager.FCMTokenManager;
import org.ums.persistent.model.PersistentFCMToken;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class FCMTokenResourceHelper extends ResourceHelper<FCMToken, MutableFCMToken, Long> {

  @Autowired
  private FCMTokenManager mManager;

  @Autowired
  private FCMTokenBuilder mBuilder;

  public JsonObject getToken(String pUserId, UriInfo mUriInfo) {
    LocalCache localCache = new LocalCache();
    FCMToken fcmToken = new PersistentFCMToken();
    try {
      fcmToken = mManager.getFCMToken(pUserId);
    } catch(EmptyResultDataAccessException e) {
    }
    return toJson(fcmToken, mUriInfo, localCache);
  }

  public Response updateFCMToken(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableFCMToken mutableFCMToken = new PersistentFCMToken();
    LocalCache localeCache = new LocalCache();
    JsonObject jsonObject = pJsonObject.getJsonObject("data");
    mBuilder.build(mutableFCMToken, jsonObject, localeCache);
    mManager.update(mutableFCMToken);
    localeCache.invalidate();
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    MutableFCMToken mutableFCMToken = new PersistentFCMToken();
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = pJsonObject.getJsonObject("data");
    mBuilder.build(mutableFCMToken, jsonObject, localCache);
    mManager.create(mutableFCMToken);
    localCache.invalidate();
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  protected ContentManager<FCMToken, MutableFCMToken, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<FCMToken, MutableFCMToken> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(FCMToken pReadonly) {
    return pReadonly.getLastModified();
  }
}
