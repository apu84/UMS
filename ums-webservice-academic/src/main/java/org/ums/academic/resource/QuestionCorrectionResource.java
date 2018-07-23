package org.ums.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by Monjur-E-Morshed on 7/11/2018.
 */

@Component
@Path("academic/questionCorrectionInfo")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class QuestionCorrectionResource extends MutableQuestionCorrectionResource {
  @GET
  @Path("/getCourses/programId/{program-id}/year/{year}/semester/{semester}")
  public JsonObject getExpelInfoList(@Context Request pRequest, @PathParam("program-id") Integer pProgramId,
      @PathParam("year") Integer pYear, @PathParam("semester") Integer pSemester) {
    return mQuestionCorrectionResourceHelper.getCourses(pProgramId, pYear, pSemester, pRequest, mUriInfo);
  }

  @GET
  @Path("/getQuestionCorrectionInfoList/semesterId/{semester-id}/examType/{examType}")
  public JsonObject getQuestionCorrectionInfoList(@Context Request pRequest,
      @PathParam("semester-id") Integer pSemesterId, @PathParam("examType") Integer pExamType) {
    return mQuestionCorrectionResourceHelper.getQuestionCorrectionInfo(pSemesterId, pExamType, pRequest, mUriInfo);
  }

  @GET
  @Path("/getExamDate/semesterId/{semester-id}/examType/{examType}/courseId/{course-id}")
  public JsonObject getExamDate(@Context Request pRequest, @PathParam("semester-id") Integer pSemesterId,
      @PathParam("examType") Integer pExamType, @PathParam("course-id") String pCourseId) {
    return mQuestionCorrectionResourceHelper.getExamDate(pSemesterId, pExamType, pCourseId, pRequest, mUriInfo);
  }

}
