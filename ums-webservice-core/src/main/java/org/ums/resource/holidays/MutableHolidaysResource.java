package org.ums.resource.holidays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 17-Jun-17.
 */
public class MutableHolidaysResource extends Resource {

  protected static final Logger mLogger = LoggerFactory.getLogger(MutableHolidaysResource.class);

  @Autowired
  HolidaysResourceHelper mHelper;

  /*
   * @POST
   * 
   * @Path("/save") public Response saveOrUpdateHolidays(final JsonObject pJsonObject) throws
   * Exception { return mHelper.saveOrUpdate(pJsonObject, mUriInfo); }
   */

  @POST
  @Path("/save")
  public Response saveOrUpdate(final JsonObject pJsonObject) throws Exception {
    return mHelper.saveOrUpdate(pJsonObject, mUriInfo);
  }

}
