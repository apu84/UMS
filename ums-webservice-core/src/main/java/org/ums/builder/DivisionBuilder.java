package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.Division;
import org.ums.domain.model.mutable.common.MutableDivision;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class DivisionBuilder implements Builder<Division, MutableDivision> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Division pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getDivisionName());
  }

  @Override
  public void build(MutableDivision pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
