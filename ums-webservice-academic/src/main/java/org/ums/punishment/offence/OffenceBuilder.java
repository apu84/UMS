package org.ums.punishment.offence;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class OffenceBuilder implements Builder<Offence, MutableOffence> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Offence pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {}

  @Override
  public void build(MutableOffence pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
