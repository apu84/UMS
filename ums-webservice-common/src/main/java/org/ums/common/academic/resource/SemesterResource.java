package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.manager.SemesterManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/academic/semester")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SemesterResource extends MutableSemesterResource {
  @Autowired
  @Qualifier("semesterManager")
  SemesterManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/program-type/{program-type}/limit/{list-limit}")
  public JsonObject getSemesterList(final @Context Request pRequest, final @PathParam("program-type") int pProgramType, final @PathParam("list-limit") int pListLimit)
      throws Exception {
    return mResourceHelper.buildSemesters(mManager.getSemesters(pProgramType, pListLimit), mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") int pObjectId) throws Exception {
    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }
  /*
  @GET
  @Path("/program-type/{program-type}/limit/{list-limit}")
  public JsonObject getSyllabusMap(final @Context Request pRequest, final @PathParam("program-type") int pProgramType, 
		  						   final @PathParam("dept") String pDeptId, final @PathParam("year") int pYear,
		  						   final @PathParam("year") int pSemester)
      throws Exception {
    return mResourceHelper.buildSemesters(mManager.getSemesters(pProgramType, pDeptId), mUriInfo);
  }
  */
}
