package org.ums.common.academic.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.manager.SemesterWithDrawalManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("academic/semesterWithdraw")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SemesterWithdrawalResource extends MutableSemesterWithdrawalResource{

  @Autowired
  SemesterWithDrawalManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception{
    return mHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/studentInfo/semester/{semesterId}/year/{year}/semester/{semester}")
  public JsonObject getStudentRecord(final @Context Request pRequest,
                                          final @PathParam("semesterId") String semesterId,
                                          final @PathParam("year") String year,
                                          final @PathParam("semester") String semester)throws Exception{
    return mHelper.getStudentRecord(Integer.parseInt(semesterId),Integer.parseInt(year),Integer.parseInt(semester),pRequest,mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") int pObjectId)throws Exception{
    return mHelper.get(pObjectId,pRequest,mUriInfo);
  }
}
