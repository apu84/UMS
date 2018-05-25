package org.ums.resource.holidays;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
  public JsonObject getHolidaysByYear(@Context HttpServletRequest pHttpServletRequest,
      final @PathParam("year") int pYear) throws Exception {
    /*
     * mLogger.trace(userAgent); System.out.println(pHttpServletRequest.getRequestURL());
     * System.out.println(pHttpServletRequest.getAuthType());
     * mLogger.trace(pHttpServletRequest.getRemoteAddr());
     */
    return mHelper.getHolidaysByYear(pYear, mUriInfo);
  }

  /*
   * @GET
   * 
   * @Path("/saveOrUpdate") public Response saveOrUpdateHolidays(final JsonObject pJsonObject, final
   * @Context Request pRequest) throws Exception { return mHelper.saveOrUpdate(pJsonObject,
   * mUriInfo); }
   */

}
