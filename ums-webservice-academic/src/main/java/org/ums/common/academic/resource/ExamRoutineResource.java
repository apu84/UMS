package org.ums.common.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Ifti on 26-Feb-16.
 */
@Component
@Path("/academic/examroutine")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ExamRoutineResource  extends MutableExamRoutineResource {

  @GET
  @Path("/semester/{semester-id}/examtype/{exam-type}")
  public JsonObject getExamRoutine(final @Context Request pRequest,
                           final @PathParam("semester-id") Integer pSemesterId,
                           final @PathParam("exam-type") Integer pExamTypeId) throws Exception {
    return mResourceHelper.getExamRoutine(pSemesterId,pExamTypeId);
  }


  @GET
  @Path("/simplified/semester/{semester-id}/examtype/{exam-type}")
  public JsonObject getExamRoutineSimplified(final @Context Request pRequest,
                                   final @PathParam("semester-id") Integer pSemesterId,
                                   final @PathParam("exam-type") String pExamTypeId) throws Exception {
    return mResourceHelper.getExamRoutineWithSemesterAndExamType(pSemesterId,Integer.parseInt(pExamTypeId),pRequest,mUriInfo);
  }

  @GET
  @Path("/examdates/semester/{semester-id}/examtype/{exam-type}")
  public JsonObject getExamDates(final @Context Request pRequest,
                                             final @PathParam("semester-id") Integer pSemesterId,
                                             final @PathParam("exam-type") String pExamTypeId) throws Exception {
    return mResourceHelper.getExamDateBySemesterAndExamType(pSemesterId,Integer.parseInt(pExamTypeId),pRequest,mUriInfo);
  }


  @GET
  @Path("/exam_routine_cci/semester/{semester-id}/examtype/{exam-type}")
  public JsonObject getExamRoutineForCCI(final @Context Request pRequest,
                                   final @PathParam("semester-id") Integer pSemesterId,
                                   final @PathParam("exam-type") Integer pExamTypeId) throws Exception {
    return mResourceHelper.getExamRoutineForCCI(pSemesterId,pExamTypeId,pRequest,mUriInfo);
  }

  @GET
  @Path("/forPublish/civil/semester/{semester-id}")
  public JsonObject getCivilExamInfo(final @Context Request pRequest,
                                     final @PathParam("semester-id") Integer pSemesterId) throws Exception{
    return mResourceHelper.getExamRoutineInfoForCivil(pSemesterId,pRequest,mUriInfo);
  }

  @GET
  @Path("/forPublish/cci/semester/{semester-id}")
  public JsonObject getCCIInfoForPublish(final @Context Request pRequest,
                                     final @PathParam("semester-id") Integer pSemesterId) throws Exception{
    return mResourceHelper.getExamRoutineForCCIForSeatPlanPublish(pSemesterId,pRequest,mUriInfo);
  }


}
