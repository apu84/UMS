package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.formatter.DateFormat;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class FCMTokenBuilder implements Builder<FCMToken, MutableFCMToken> {

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, FCMToken pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {}

  @Override
  public void build(MutableFCMToken pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {}
}
