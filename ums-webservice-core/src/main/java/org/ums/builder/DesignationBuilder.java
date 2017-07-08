package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.mutable.MutableDesignation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class DesignationBuilder implements Builder<Designation, MutableDesignation> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Designation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getDesignationName());
  }

  @Override
  public void build(MutableDesignation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
