package org.ums.academic.resource.optCourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resource.optCourse.optCourseHelper.OptOfferedGroupSubGroupMapResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class MutableOptOfferedGroupSubGroupMapResource extends Resource {
  @Autowired
  OptOfferedGroupSubGroupMapResourceHelper mapResourceHelper;

  /*
   * @POST
   * 
   * @Path("/addRecords")
   * 
   * @Produces({MediaType.APPLICATION_JSON}) public Response addRecords(final JsonObject
   * pJsonObject) throws Exception { return mHelper.post(pJsonObject, mUriInfo); }
   * 
   * @POST
   * 
   * @Path("/updateRecords")
   * 
   * @Produces({MediaType.APPLICATION_JSON}) public Response updateRecords(final JsonObject
   * pJsonObject) throws Exception { return mHelper.UpdateRecords(pJsonObject, mUriInfo); }
   * 
   * @PUT
   * 
   * @Path("/deleteRecords")
   * 
   * @Produces({MediaType.APPLICATION_JSON}) public Response deleteRecords(final JsonObject
   * pJsonObject) { return mHelper.deleteRecords(pJsonObject, mUriInfo); }
   */

}
