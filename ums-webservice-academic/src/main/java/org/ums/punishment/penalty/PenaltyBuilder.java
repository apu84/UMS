package org.ums.punishment.penalty;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class PenaltyBuilder implements Builder<Penalty, MutablePenalty> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Penalty pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {}

  @Override
  public void build(MutablePenalty pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
