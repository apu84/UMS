package org.ums.academic.resource.optCourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.optCourse.optCourseHelper.OptSeatAllocationResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public class MutableOptSeatAllocationResource extends Resource {
  @Autowired
  OptSeatAllocationResourceHelper mHelper;

  @POST
  @Path("/addRecords")
  @Produces({MediaType.APPLICATION_JSON})
  public Response addRecords(final JsonObject pJsonObject) throws Exception {
    return mHelper.post(pJsonObject, mUriInfo);
  }
}
