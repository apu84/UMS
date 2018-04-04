package org.ums.punishment;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class PunishmentBuilder implements Builder<Punishment, MutablePunishment> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Punishment pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {}

  @Override
  public void build(MutablePunishment pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
