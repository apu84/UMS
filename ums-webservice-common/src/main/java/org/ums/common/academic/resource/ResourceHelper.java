package org.ums.common.academic.resource;


import org.ums.domain.model.Semester;

import javax.json.JsonObject;
import javax.ws.rs.core.UriInfo;

public abstract class ResourceHelper<O> {

  public abstract O load(final String pObjectId) throws Exception;

  public abstract JsonObject toJson(final Semester pObject, final UriInfo pUriInfo) throws Exception;

  public abstract void delete(final Semester pObject) throws Exception;

  public abstract void put(final Semester pObject, final JsonObject pJsonObject) throws Exception;
}
