package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.manager.ClassRoomManager;
import org.ums.manager.ExamRoutineManager;

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



}
