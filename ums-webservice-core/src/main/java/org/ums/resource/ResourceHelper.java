package org.ums.resource;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.json.*;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.util.StringUtils;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.Identifier;
import org.ums.filter.ListFilter;
import org.ums.filter.ListFilterImpl;
import org.ums.formatter.DateFormat;
import org.ums.manager.ContentManager;
import org.ums.resource.filter.FilterItem;

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
      M mutable = mutableInstanceType.getConstructor().newInstance();
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

  protected List<ListFilter> buildFilterQuery(JsonObject pFilter) {
    List<ListFilter> filterCriteria = new ArrayList<>();
    if(pFilter.containsKey("entries")) {
      JsonArray entries = pFilter.getJsonArray("entries");
      entries.forEach((entry) -> {
        JsonObject filter = (JsonObject) entry;
        Object value = null;
        if(!StringUtils.isEmpty(filter.getJsonObject("filter").getString("type"))
            && filter.getJsonObject("filter").getString("type").equalsIgnoreCase("date")) {
          value = getDateFormatter().parse(filter.getJsonObject("value").getString("value"));
        }
        else if(filter.getJsonObject("value").get("value").getValueType() == JsonValue.ValueType.NUMBER) {
          value = filter.getJsonObject("value").getInt("value");
        }
        else if(filter.getJsonObject("value").get("value").getValueType() == JsonValue.ValueType.STRING) {
          value = filter.getJsonObject("value").getString("value");
        }
        else if(filter.getJsonObject("value").get("value").getValueType() == JsonValue.ValueType.TRUE
            || filter.getJsonObject("value").get("value").getValueType() == JsonValue.ValueType.FALSE) {
          value = filter.getJsonObject("value").getBoolean("value");
        }
        else {
          filter.getJsonObject("value").get("value");
        }
        filterCriteria.add(new ListFilterImpl(filter.getJsonObject("filter").getString("value"), value));
      });
    }
    return filterCriteria;
  }

  protected JsonArray getFilterJson(List<FilterItem> pFilterItems) {
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    pFilterItems.forEach((pFilterItem) -> {
      JsonObjectBuilder filter = Json.createObjectBuilder();
      filter.add("label", pFilterItem.getLabel());
      filter.add("value", pFilterItem.getValue());
      filter.add("type", pFilterItem.getType().toString().toLowerCase());
      if(pFilterItem.getOptions().size() > 0) {
        JsonArrayBuilder options = Json.createArrayBuilder();
        pFilterItem.getOptions().forEach((pFilterItemOption) -> {
          JsonObjectBuilder option = Json.createObjectBuilder();
          option.add("label", pFilterItemOption.getLabel());
          if(pFilterItemOption.getValue() instanceof Integer) {
            option.add("value", (Integer) pFilterItemOption.getValue());
          }
          else if(pFilterItemOption.getValue() instanceof Boolean) {
            option.add("value", (Boolean) pFilterItemOption.getValue());
          }
          else if(pFilterItemOption.getValue() instanceof String) {
            option.add("value", (String) pFilterItemOption.getValue());
          }
          options.add(option);
        });
        filter.add("options", options);
      }
      arrayBuilder.add(filter);
    });
    return arrayBuilder.build();
  }

  public abstract Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception;

  protected abstract ContentManager<R, M, I> getContentManager();

  protected abstract Builder<R, M> getBuilder();

  protected abstract String getETag(R pReadonly);

  protected DateFormat getDateFormatter() {
    return null;
  }
}
