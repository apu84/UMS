package org.ums.resource.holidays;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 17-Jun-17.
 */
@Component
@Path("/holidays")
public class HolidaysResource extends MutableHolidaysResource {

  @GET
  @Path("/year/{year}")
  public JsonObject getHolidaysByYear(final @PathParam("year") int pYear, final @Context Request pRequest)
      throws Exception {
    return mHelper.getHolidaysByYear(pYear, mUriInfo);
  }

  @GET
  @Path("/saveOrUpdate")
  public Response saveOrUpdateHolidays(final JsonObject pJsonObject, final @Context Request pRequest) throws Exception {
    return mHelper.saveOrUpdate(pJsonObject, mUriInfo);
  }

}
