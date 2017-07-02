package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.District;
import org.ums.domain.model.mutable.common.MutableDistrict;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class DistrictBuilder implements Builder<District, MutableDistrict> {

  @Override
  public void build(JsonObjectBuilder pBuilder, District pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("division_id", pReadOnly.getDivisionId());
    pBuilder.add("name", pReadOnly.getDistrictName());
    pBuilder.add("lastModified", pReadOnly.getLastModified() == null ? "" : pReadOnly.getLastModified());
  }

  @Override
  public void build(MutableDistrict pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    // Do Nothing
  }
}
