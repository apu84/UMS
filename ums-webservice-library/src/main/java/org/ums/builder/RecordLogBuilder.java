package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.RecordLog;
import org.ums.domain.model.mutable.library.MutableRecordLog;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class RecordLogBuilder implements Builder<RecordLog, MutableRecordLog> {

  @Override
  public void build(JsonObjectBuilder pBuilder, RecordLog pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableRecordLog pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
