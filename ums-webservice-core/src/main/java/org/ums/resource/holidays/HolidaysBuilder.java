package org.ums.resource.holidays;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.Holidays;
import org.ums.domain.model.mutable.common.MutableHolidays;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 17-Jun-17.
 */
@Component
public class HolidaysBuilder implements Builder<Holidays, MutableHolidays> {

  @Override
  public void build(JsonObjectBuilder pBuilder, Holidays pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("holidayTypeId", pReadOnly.getHolidayType().getId());
    pBuilder.add("holidayTypeName", pReadOnly.getHolidayType().getHolidayName());
    pBuilder.add("year", pReadOnly.getYear());
    pBuilder.add("fromDate", pReadOnly.getFromDate());
    pBuilder.add("toDate", pReadOnly.getToDate());
  }

  @Override
  public void build(MutableHolidays pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setId(Long.valueOf(pJsonObject.getString("id")));
    pMutable.setHolidayTypeId(Long.valueOf(pJsonObject.getString("holidayTypeId")));
    pMutable.setYear(pJsonObject.getInt("year"));
    pMutable.setFromDate(pJsonObject.getString("fromDate"));
    pMutable.setToDate(pJsonObject.getString("toDate"));
  }
}
