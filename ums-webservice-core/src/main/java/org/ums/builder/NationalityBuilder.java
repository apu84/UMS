package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.Nationality;
import org.ums.domain.model.mutable.common.MutableNationality;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class NationalityBuilder implements Builder<Nationality, MutableNationality> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Nationality pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getNationality());
  }

  @Override
  public void build(MutableNationality pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
