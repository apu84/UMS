package org.ums.common.academic.resource;


import org.ums.academic.builder.Builder;
import org.ums.domain.model.EditType;
import org.ums.domain.model.Mutable;
import org.ums.manager.ContentManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.*;
import java.util.List;

public abstract class ResourceHelper<R extends EditType<M>, M extends Mutable, I> {

  protected R load(final I pObjectId) throws Exception {
    return getContentManager().get(pObjectId);
  }

  protected Response get(final I pObjectId, final Request pRequest, final UriInfo pUriInfo) throws Exception {
    R readOnly = load(pObjectId);
    Response.ResponseBuilder builder;
    //Calculate the ETag on last modified date of user resource
    EntityTag etag = new EntityTag(getEtag(readOnly));
    //Verify if it matched with etag available in http request
    builder = pRequest.evaluatePreconditions(etag);

    if (builder == null) {
      builder = Response.ok(toJson(readOnly, pUriInfo));
      builder.tag(etag);
    }

    CacheControl cacheControl = new CacheControl();
    cacheControl.setMaxAge(86400);
    cacheControl.setPrivate(true);
    builder.cacheControl(cacheControl);

    return builder.build();
  }

  protected JsonObject toJson(final R pObject, final UriInfo pUriInfo) throws Exception {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    for (Builder<R, M> builder : getBuilders()) {
      builder.build(jsonObjectBuilder, pObject, pUriInfo);
    }
    return jsonObjectBuilder.build();
  }

  protected void delete(final Mutable pObject) throws Exception {
    pObject.delete();
  }

  protected Response put(final R pObject, final Request pRequest,
                         final String pIfMatch, final JsonObject pJsonObject) throws Exception {
    if (pIfMatch == null) {
      return Response.status(Response.Status.BAD_REQUEST).entity("No If-Match header found.").build();
    }
    EntityTag etag = new EntityTag(getEtag(pObject));

    Response.ResponseBuilder preconditionResponse = pRequest.evaluatePreconditions(etag);
    // client is not up to date (send back 412)
    if (preconditionResponse != null) {
      return preconditionResponse.build();
    }

    M mutable = pObject.edit();
    for (Builder<R, M> builder : getBuilders()) {
      builder.build(mutable, pJsonObject);
    }
    mutable.commit(true);

    return Response.noContent().build();
  }

  protected abstract Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception;

  protected abstract ContentManager<R, M, I> getContentManager();

  protected abstract List<Builder<R, M>> getBuilders();

  protected abstract String getEtag(R pReadonly);
}
