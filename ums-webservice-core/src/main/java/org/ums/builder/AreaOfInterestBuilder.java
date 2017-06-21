package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AreaOfInterest;
import org.ums.domain.model.mutable.MutableAreaOfInterest;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AreaOfInterestBuilder implements Builder<AreaOfInterest, MutableAreaOfInterest> {
  @Override
  public void build(JsonObjectBuilder pBuilder, AreaOfInterest pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getAreaOfInterest());
  }

  @Override
  public void build(MutableAreaOfInterest pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
