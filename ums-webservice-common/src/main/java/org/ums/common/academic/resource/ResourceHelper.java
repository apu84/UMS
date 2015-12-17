package org.ums.common.academic.resource;


import org.ums.academic.builder.Builder;
import org.ums.domain.model.Mutable;
import org.ums.manager.ContentManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

public abstract class ResourceHelper<R, M, I> {

  public R load(final I pObjectId) throws Exception {
    return getContentManager().get(pObjectId);
  }

  public JsonObject toJson(final R pObject, final UriInfo pUriInfo) throws Exception {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    for (Builder<R, M> builder : getBuilders()) {
      builder.build(jsonObjectBuilder, pObject, pUriInfo);
    }
    return jsonObjectBuilder.build();
  }

  public void delete(final Mutable pObject) throws Exception {
    pObject.delete();
  }

  public abstract void put(final R pObject, final JsonObject pJsonObject) throws Exception;

  public abstract Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception;

  public abstract ContentManager<R, M, I> getContentManager();

  public abstract List<Builder<R, M>> getBuilders();
}
