package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.manager.SpStudentManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by My Pc on 4/28/2016.
 */

@Component
@Path("academic/spStudent")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SpStudentResource  extends MutableSpStudentResource{

  @Autowired
  SpStudentManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll()throws Exception{
    return mHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/program/{programId}/year/{year}/semester/{semester}/status/{status}")
  public JsonObject getByProgramYearSemesterStatus(final @Context Request pRequest, final @PathParam("programId") int programId,
                                                   final @PathParam("year") int year,
                                                   final @PathParam("semester") int semester,
                                                   final @PathParam("status") int status)throws Exception{
    return mHelper.getStudentByProgramYearSemesterStatus(programId,year,semester,status,pRequest,mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") String pObjectId)throws Exception{
    return mHelper.get(pObjectId,pRequest,mUriInfo);
  }
}
