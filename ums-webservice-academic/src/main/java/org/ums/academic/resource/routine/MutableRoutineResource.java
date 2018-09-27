package org.ums.academic.resource.routine;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.logs.DeleteLog;
import org.ums.logs.PutLog;
import org.ums.resource.Resource;
import org.ums.services.academic.routine.helper.RoutineErrorLog;

import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MutableRoutineResource extends Resource {

  @Autowired
  RoutineResourceHelper mRoutineResourceHelper;

  @PUT
  @Path("/saveOrUpdate")
  @PutLog(message = "Saving or updating routine data")
  public JsonArray createOrUpdateRoutine(@Context HttpServletRequest pHttpServletRequest, final JsonArray pJsonArray) {
    return mRoutineResourceHelper.saveOrUpdateRoutine(pJsonArray, mUriInfo);
  }

  @DELETE
  @Path("/id/{id}")
  @DeleteLog(message = "Requested for deleting routine data")
  public Response delete(@Context HttpServletRequest pHttpServletRequest, @PathParam("id") String id) throws Exception {
    return mRoutineResourceHelper.delete(Long.parseLong(id));
  }

  @POST
  @Path("/upload")
  @Consumes({MediaType.MULTIPART_FORM_DATA})
  public List<RoutineErrorLog> uploadRoutineTemplate(@FormDataParam("file") File pFile,
      @FormDataParam("semesterId") Integer pSemesterId, @FormDataParam("programId") Integer pProgramId)
      throws Exception, IOException, InvalidFormatException {
    return mRoutineResourceHelper.uploadFile(pFile, pSemesterId, pProgramId);
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
