package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Fine;
import org.ums.domain.model.mutable.library.MutableFine;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class FineBuilder implements Builder<Fine, MutableFine> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Fine pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableFine pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
