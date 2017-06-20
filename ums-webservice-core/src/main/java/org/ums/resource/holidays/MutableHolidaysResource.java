package org.ums.resource.holidays;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 17-Jun-17.
 */
public class MutableHolidaysResource extends Resource {

  @Autowired
  HolidaysResourceHelper mHelper;

  @PUT
  @Path("/save")
  public Response saveOrUpdateHolidays(final JsonObject pJsonObject) throws Exception {
    return mHelper.saveOrUpdate(pJsonObject, mUriInfo);
  }

}
