package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.readOnly.User;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class UserBuilder implements Builder<User, MutableUser> {
  @Override
  public void build(JsonObjectBuilder pBuilder, User pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
  }

  @Override
  public void build(MutableUser pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {
    // Do Nothing
  }
}
