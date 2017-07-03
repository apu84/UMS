package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.BloodGroup;
import org.ums.domain.model.mutable.common.MutableBloodGroup;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class BloodGroupBuilder implements Builder<BloodGroup, MutableBloodGroup> {
  @Override
  public void build(JsonObjectBuilder pBuilder, BloodGroup pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getBloodGroup());
  }

  @Override
  public void build(MutableBloodGroup pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
