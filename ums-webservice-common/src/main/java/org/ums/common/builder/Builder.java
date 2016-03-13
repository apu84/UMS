package org.ums.common.builder;

import org.ums.cache.LocalCache;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

//TODO: Move this to webservice core
public interface Builder<R, M> {
  void build(final JsonObjectBuilder pBuilder, final R pReadOnly, final UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception;
  void build(final M pMutable, JsonObject pJsonObject, final LocalCache pLocalCache) throws Exception;
}