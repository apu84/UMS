package org.ums.punishment.authority;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AuthorityBuilder implements Builder<Authority, MutableAuthority> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Authority pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {}

  @Override
  public void build(MutableAuthority pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
