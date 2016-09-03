package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Semester;
import org.ums.manager.SemesterManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/academic/semester")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SemesterResource extends MutableSemesterResource {
  @Autowired
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
    List<Semester> semesters = mManager.getSemesters(pProgramType,pListLimit);
    return mResourceHelper.buildSemesters(mManager.getSemesters(pProgramType, pListLimit), mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") int pObjectId) throws Exception {
    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }


}
