package org.ums.resource;

import javassist.bytecode.annotation.NoSuchClassError;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.fee.latefee.MutableUGLateFee;
import org.ums.fee.latefee.PersistentUGLateFee;
import org.ums.manager.ContentManager;

import javax.json.*;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class ResourceHelper<R extends EditType<M> & Identifier, M extends Editable & Identifier, I> {

  public R load(final I pObjectId) {
    return getContentManager().get(pObjectId);
  }

  public Response get(final I pObjectId, final Request pRequest, final UriInfo pUriInfo) throws Exception {
    R readOnly = load(pObjectId);
    Response.ResponseBuilder builder = null;
    // Calculate the ETag on last modified date of user resource
    EntityTag eTag = new EntityTag(getETag(readOnly));

    // Verify if it matched with eTag available in http request
    // builder = pRequest.evaluatePreconditions(eTag);
    if(builder == null) {
      LocalCache localCache = new LocalCache();
      builder = Response.ok(toJson(readOnly, pUriInfo, localCache));
      builder.tag(eTag);
      localCache.invalidate();
    }

    // CacheControl cacheControl = new CacheControl();
    // cacheControl.setMaxAge(86400);
    // cacheControl.setPrivate(true);
    // builder.cacheControl(cacheControl);

    return builder.build();
  }

  public JsonObject getAll(final UriInfo pUriInfo) throws Exception {
    List<R> readOnlys = getContentManager().getAll();
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(R readOnly : readOnlys) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  protected JsonObject toJson(final R pObject, final UriInfo pUriInfo, final LocalCache pLocalCache) {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    getBuilder().build(jsonObjectBuilder, pObject, pUriInfo, pLocalCache);
    return jsonObjectBuilder.build();
  }

  protected JsonObject buildJsonResponse(final List<R> pObjectList, final UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(R readOnly : pObjectList) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  protected boolean hasUpdatedVersion(R latestEntity, R toCheckWith) {
    return getETag(latestEntity).equalsIgnoreCase(getETag(toCheckWith));
  }

  protected <P extends M> List<M> readEntities(JsonObject pJsonObject, Class<P> mutableInstanceType)
      throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    List<M> mutables = new ArrayList<>();
    for(JsonValue value : entries) {
      JsonObject entry = (JsonObject) value;
      M mutable = mutableInstanceType.getConstructor(String.class).newInstance();
      getBuilder().build(mutable, entry, localCache);
      mutables.add(mutable);
    }
    return mutables;
  }

  protected boolean isValidUpdateOfEntities(List<R> latest, List<M> updated) {
    for(R latestR : latest) {
      for(M updatedR : updated) {
        // loosely checking on id
        if(latestR.getId().toString().equalsIgnoreCase(updatedR.getId().toString())
            && !hasUpdatedVersion(latestR, (R) updatedR)) {
          return false;
        }
      }
    }
    return true;
  }

  public Response delete(final I pObjectId) throws Exception {
    R readOnly = load(pObjectId);
    readOnly.edit().delete();
    return Response.noContent().build();
  }

  public Response put(final I pObjectId, final Request pRequest, final String pIfMatch, final JsonObject pJsonObject)
      throws Exception {
    if(pIfMatch == null) {
      return Response.status(Response.Status.BAD_REQUEST).entity("No If-Match header found.").build();
    }
    R readOnly = load(pObjectId);
    EntityTag eTag = new EntityTag(getETag(readOnly));

    Response.ResponseBuilder preconditionResponse = pRequest.evaluatePreconditions(eTag);
    // client is not up to date (send back 412)
    if(preconditionResponse != null) {
      return preconditionResponse.build();
    }

    M mutable = readOnly.edit();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutable, pJsonObject, localCache);
    mutable.update();
    localCache.invalidate();

    return Response.noContent().build();
  }

  public abstract Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception;

  protected abstract ContentManager<R, M, I> getContentManager();

  protected abstract Builder<R, M> getBuilder();

  protected abstract String getETag(R pReadonly);

}
