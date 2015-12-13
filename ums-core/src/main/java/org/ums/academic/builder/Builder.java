package org.ums.academic.builder;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

public interface Builder<R, M> {
  void build(final JsonObjectBuilder pBuilder, final R pReadOnly, final UriInfo pUriInfo) throws Exception;

  void build(final M pMutable, JsonObject pJsonObject) throws Exception;
}