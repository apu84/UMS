package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.mutable.library.MutableCirculation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class CirculationBuilder implements Builder<Circulation, MutableCirculation> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Circulation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableCirculation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
