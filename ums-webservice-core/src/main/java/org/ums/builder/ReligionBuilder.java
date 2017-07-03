package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.Religion;
import org.ums.domain.model.mutable.common.MutableReligion;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ReligionBuilder implements Builder<Religion, MutableReligion> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Religion pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getReligion());
  }

  @Override
  public void build(MutableReligion pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
