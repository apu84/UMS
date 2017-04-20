package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.Thana;
import org.ums.domain.model.mutable.common.MutableThana;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ThanaBuilder implements Builder<Thana, MutableThana> {

  @Override
  public void build(JsonObjectBuilder pBuilder, Thana pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getThanaId());
    pBuilder.add("district_id", pReadOnly.getDistrictId());
    pBuilder.add("name", pReadOnly.getThanaName());
    pBuilder.add("lastModified", pReadOnly.getLastModified() == null ? "" : pReadOnly.getLastModified());
  }

  @Override
  public void build(MutableThana pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    // Do Nothing
  }
}
