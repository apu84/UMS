package org.ums.resource.holidays;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Monjur-E-Morshed on 17-Jun-17.
 */
@Component
@Path("/holidayType")
public class HolidayTypeResource extends MutableHolidayTypeResource {

  @GET
  @Path("/all")
  public JsonObject getAllHolidayTypes(final @Context Request pRequest) throws Exception {
    return mHelper.getAllHolidayTypes(mUriInfo);
  }
}
