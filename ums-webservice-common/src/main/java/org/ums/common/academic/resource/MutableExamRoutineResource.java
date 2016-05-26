package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.ExamRoutineResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 26-Feb-16.
 */
public class MutableExamRoutineResource  extends Resource {

  @Autowired
  ExamRoutineResourceHelper mResourceHelper;

  @PUT
  @Path( "/semester/{semester-id}/examtype/{exam-type}")
  public Response saveExamRoutine(final @PathParam("semester-id") String pSemesterId,
                                    final @PathParam("exam-type") String pExamType,
                                    final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.save(pJsonObject,Integer.parseInt(pSemesterId),Integer.parseInt(pExamType));
  }

}
