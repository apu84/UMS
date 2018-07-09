package org.ums.academic.resource.routine;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.PutLog;
import org.ums.resource.Resource;

import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

public class MutableRoutineResource extends Resource {

  @Autowired
  RoutineResourceHelper mRoutineResourceHelper;

  @PUT
  @Path("/saveOrUpdate")
  @PutLog(message = "Saving or updating routine data")
  public JsonArray createOrUpdateRoutine(@Context HttpServletRequest pHttpServletRequest, final JsonArray pJsonArray) {
    return mRoutineResourceHelper.saveOrUpdateRoutine(pJsonArray, mUriInfo);
  }

  /*
   * @POST
   * 
   * @PostLog(message = "Created new routine information") public Response createRoutine(final
   * 
   * @Context HttpServletRequest pHttpServletRequest, final JsonObject pJsonObject) { return
   * mRoutineResourceHelper.post(pJsonObject, mUriInfo); }
   * 
   * @PUT
   * 
   * @PostLog(message = "Updated routine information") public Response updateRoutine(@Context
   * HttpServletRequest httpServletRequest, final JsonObject pJsonObject, final @Context Request
   * pRequest, final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader) throws Exception { return
   * mRoutineResourceHelper.put(Long.parseLong(pJsonObject.getString("id")), pRequest,
   * pIfMatchHeader, pJsonObject); }
   * 
   * @DELETE
   * 
   * @Path(PATH_PARAM_OBJECT_ID)
   * 
   * @PostLog(message = "Deleted routine information") public Response deleteRoutine(@Context
   * HttpServletRequest httpServletRequest, final @PathParam("object-id") String objectId) throws
   * Exception { return mRoutineResourceHelper.delete(Long.parseLong(objectId)); }
   */

}
