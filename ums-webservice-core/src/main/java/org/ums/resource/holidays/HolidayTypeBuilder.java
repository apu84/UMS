package org.ums.resource.holidays;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.HolidayType;
import org.ums.domain.model.mutable.common.MutableHolidayType;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 17-Jun-17.
 */
@Component
public class HolidayTypeBuilder implements Builder<HolidayType, MutableHolidayType> {
  @Override
  public void build(JsonObjectBuilder pBuilder, HolidayType pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("name", pReadOnly.getHolidayName());
    pBuilder.add("moonDependency", pReadOnly.getMoonDependency().getId());
  }

  @Override
  public void build(MutableHolidayType pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
