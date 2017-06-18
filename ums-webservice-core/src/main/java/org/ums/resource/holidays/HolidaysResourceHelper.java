package org.ums.resource.holidays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.Holidays;
import org.ums.domain.model.mutable.common.MutableHolidays;
import org.ums.manager.common.HolidaysManager;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 17-Jun-17.
 */
@Component
public class HolidaysResourceHelper extends ResourceHelper<Holidays, MutableHolidays, Long> {

  @Autowired
  HolidaysManager mHolidaysManager;

  @Autowired
  HolidaysBuilder mHolidaysBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public JsonObject getHolidaysByYear(int pYear, UriInfo pUriInfo) {
    List<Holidays> holidaysList = getContentManager().getHolidays(pYear);
    return getJsonObject(pUriInfo, holidaysList);
  }

  private JsonObject getJsonObject(UriInfo pUriInfo, List<Holidays> pHolidaysList) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Holidays holidays : pHolidaysList) {
      JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
      getBuilder().build(objectBuilder, holidays, pUriInfo, localCache);
      children.add(objectBuilder);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected HolidaysManager getContentManager() {
    return mHolidaysManager;
  }

  @Override
  protected Builder<Holidays, MutableHolidays> getBuilder() {
    return mHolidaysBuilder;
  }

  @Override
  protected String getETag(Holidays pReadonly) {
    return pReadonly.getLastModified();
  }
}
