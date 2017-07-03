package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.RelationType;
import org.ums.domain.model.mutable.common.MutableRelationType;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class RelationTypeBuilder implements Builder<RelationType, MutableRelationType> {
  @Override
  public void build(JsonObjectBuilder pBuilder, RelationType pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getRelationType());
  }

  @Override
  public void build(MutableRelationType pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
