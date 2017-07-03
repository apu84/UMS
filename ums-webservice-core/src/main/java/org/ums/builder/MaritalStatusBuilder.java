package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.MaritalStatus;
import org.ums.domain.model.mutable.common.MutableMaritalStatus;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class MaritalStatusBuilder implements Builder<MaritalStatus, MutableMaritalStatus> {
  @Override
  public void build(JsonObjectBuilder pBuilder, MaritalStatus pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getMaritalStatus());
  }

  @Override
  public void build(MutableMaritalStatus pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
