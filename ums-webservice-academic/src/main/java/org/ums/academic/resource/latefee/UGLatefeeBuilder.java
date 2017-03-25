package org.ums.academic.resource.latefee;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.fee.latefee.MutableUGLateFee;
import org.ums.fee.latefee.UGLateFee;

@Component
public class UGLatefeeBuilder implements Builder<UGLateFee, MutableUGLateFee> {
  @Override
  public void build(JsonObjectBuilder pBuilder, UGLateFee pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableUGLateFee pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
