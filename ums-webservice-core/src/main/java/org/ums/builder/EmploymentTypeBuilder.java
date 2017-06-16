package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.EmploymentType;
import org.ums.domain.model.mutable.MutableEmploymentType;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class EmploymentTypeBuilder implements Builder<EmploymentType, MutableEmploymentType> {
  @Override
  public void build(JsonObjectBuilder pBuilder, EmploymentType pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("type", pReadOnly.getType());
  }

  @Override
  public void build(MutableEmploymentType pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
