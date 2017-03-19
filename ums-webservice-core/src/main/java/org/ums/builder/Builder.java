package org.ums.builder;

import org.ums.cache.LocalCache;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

public interface Builder<R, M> {
  void build(final JsonObjectBuilder pBuilder, final R pReadOnly, final UriInfo pUriInfo, final LocalCache pLocalCache);

  void build(final M pMutable, JsonObject pJsonObject, final LocalCache pLocalCache);
}
