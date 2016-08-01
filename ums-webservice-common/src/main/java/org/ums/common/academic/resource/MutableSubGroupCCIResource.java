package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.SubGroupCCIResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by My Pc on 7/23/2016.
 */
public class MutableSubGroupCCIResource extends Resource{

  @Autowired
  SubGroupCCIResourceHelper mHelper;

  @POST
  public Response createSubGroup(final JsonObject pJsonObject) throws Exception{
    return mHelper.post(pJsonObject,mUriInfo);
  }

  @PUT
  public Response updateSubGroup(
      final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
      final JsonObject pJsonObject
  )throws Exception{
    return mHelper.put(pJsonObject.getInt("id"),pRequest,pIfMatchHeader,pJsonObject);
  }

  @POST
  @Path("/put/semester/{semester-id}/examDate/{exam-date}")
  public Response save(final @PathParam("semester-id") String pSemesterId,
                       final @PathParam("exam-date") String pExamDate,
                       final @Context Request pRequest,
                       final JsonObject pJsonObject)throws Exception{

    return mHelper.saveData(Integer.parseInt(pSemesterId),pExamDate,pJsonObject,mUriInfo);
  }

  @PUT
  @Path("/put/semester/{semesterId}/examDate/{examdate}")
  public Response saveAllSubGroupInfo(

                                      final @PathParam("semesterId") Integer pSemesterId,
                                      final @PathParam("examdate") String pExamDate,
                                      final JsonObject pJsonObject) throws Exception{
    return mHelper.saveData(pSemesterId,pExamDate,pJsonObject,mUriInfo);
  }


  @DELETE
  @Path("/semesterId/{semesterId}/examDate/{examDate}")
  public Response deleteBySemesterAndExamDate(final @PathParam("semesterId") String pSemesterId, final @PathParam("examDate") String pExamDate)throws Exception{
    return mHelper.deleteBySemesterAndExamDate(Integer.parseInt(pSemesterId),pExamDate);
  }
}
