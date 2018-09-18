package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.usermanagement.role.MutableRole;
import org.ums.usermanagement.role.Role;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class RoleBuilder implements Builder<Role, MutableRole> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Role pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getName());
    pBuilder.add("label", pReadOnly.getLabel());
  }

  @Override
  public void build(MutableRole pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
