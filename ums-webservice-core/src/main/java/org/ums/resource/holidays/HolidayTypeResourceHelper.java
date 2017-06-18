package org.ums.resource.holidays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.HolidayType;
import org.ums.domain.model.mutable.common.MutableHolidayType;
import org.ums.manager.common.HolidayTypeManager;
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
public class HolidayTypeResourceHelper extends ResourceHelper<HolidayType, MutableHolidayType, Long> {

  @Autowired
  private HolidayTypeManager mHolidayTypeManager;

  @Autowired
  private HolidayTypeBuilder mHolidayTypeBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  protected JsonObject getAllHolidayTypes(UriInfo pUriInfo) throws Exception {
    List<HolidayType> holidayTypes = getContentManager().getAll();
    return getJsonObject(pUriInfo, holidayTypes);
  }

  private JsonObject getJsonObject(UriInfo pUriInfo, List<HolidayType> pHolidayTypes) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(HolidayType holidayType : pHolidayTypes) {
      JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
      getBuilder().build(objectBuilder, holidayType, pUriInfo, localCache);
      children.add(objectBuilder);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected HolidayTypeManager getContentManager() {
    return mHolidayTypeManager;
  }

  @Override
  protected Builder<HolidayType, MutableHolidayType> getBuilder() {
    return mHolidayTypeBuilder;
  }

  @Override
  protected String getETag(HolidayType pReadonly) {
    return pReadonly.getLastModified();
  }
}
