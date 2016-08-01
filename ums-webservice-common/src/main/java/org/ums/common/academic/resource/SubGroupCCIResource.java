package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.manager.SubGroupCCIManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by My Pc on 7/23/2016.
 */

@Component
@Path("academic/subGroupCCI")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SubGroupCCIResource extends MutableSubGroupCCIResource{

  @Autowired
  SubGroupCCIManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception{
    return mHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/semester/{semester-id}/examDate/{exam-date}")
  public JsonObject getBySemesterAndExamDate(final @Context Request pRequest,
                                             final @PathParam("semester-id") Integer pSemesterId,
                                             final @PathParam("exam-date") String pExamDate)throws Exception
  {
    return mHelper.getBySemesterAndExamDate(pSemesterId,pExamDate,pRequest,mUriInfo);
  }
  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest,final @PathParam("object-id") int pObjectId) throws Exception{
    return mHelper.get(pObjectId,pRequest,mUriInfo);
  }
}
