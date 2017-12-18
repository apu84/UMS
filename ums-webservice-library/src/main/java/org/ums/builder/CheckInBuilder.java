package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.CheckIn;
import org.ums.domain.model.mutable.library.MutableCheckIn;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class CheckInBuilder implements Builder<CheckIn, MutableCheckIn> {

  @Override
  public void build(JsonObjectBuilder pBuilder, CheckIn pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableCheckIn pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
